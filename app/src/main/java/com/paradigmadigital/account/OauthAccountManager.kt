package com.paradigmadigital.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.paradigmadigital.api.model.Login
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class OauthAccountManager
@Inject constructor(
        context: Context
) {

    val accountManager = AccountManager.get(context)

    fun isLoggedIn(): Boolean {
        val accounts = accountManager.getAccountsByType(AccountAuthenticator.ARG_ACCOUNT_TYPE)
        if (accounts.isEmpty()) return false

        return getToken(accounts.get(0)) != null
    }


    fun addAccount(user: Login) {
        //Account name will be package and we will use the password field for the user email
        val account = Account(
                AccountAuthenticator.ARG_ACCOUNT_TYPE,
                AccountAuthenticator.ARG_ACCOUNT_TYPE)

        accountManager.addAccountExplicitly(account, user.email, null)
        accountManager.setAuthToken(account, AccountAuthenticator.ARG_AUTH_TYPE, user.token)
    }

    fun logout() {
        val accounts = accountManager.getAccountsByType(AccountAuthenticator.ARG_ACCOUNT_TYPE)
        if (accounts.isEmpty()) return

        val token = getToken(accounts.get(0))
        if (token == null) return

        accountManager.invalidateAuthToken(AccountAuthenticator.ARG_ACCOUNT_TYPE, token)
    }

    private fun getToken(account: Account): String? {
        val future = accountManager.getAuthToken(
                account,
                AccountAuthenticator.ARG_ACCOUNT_TYPE,
                null,
                false,
                null,
                null)

        val token=future.getResult(500, TimeUnit.MILLISECONDS).getString(AccountManager.KEY_AUTHTOKEN)
        return token
    }

}