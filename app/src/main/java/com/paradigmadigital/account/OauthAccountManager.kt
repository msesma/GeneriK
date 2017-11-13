package com.paradigmadigital.account

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerFuture
import android.content.Context
import android.os.Bundle
import com.paradigmadigital.api.model.Login
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject




class OauthAccountManager
@Inject constructor(
        val context: Context
) {

    companion object {
        private val UID = "uid"
        private val NAME = "name"
        private val PHONE = "phone"
    }

    val accountManager = AccountManager.get(context)
    val accounts
        get() = accountManager.getAccountsByType(AccountAuthenticator.ARG_ACCOUNT_TYPE)

    fun isLoggedIn(): Boolean {
        if (accounts.isEmpty()) return false

        val token = getToken(accounts.get(0))
        return !token.isNullOrEmpty()
    }

    fun addAccount(user: Login) {
        //The account name will be the app package, we will use the password field for the user email
        val account = Account(
                AccountAuthenticator.ARG_ACCOUNT_TYPE,
                AccountAuthenticator.ARG_ACCOUNT_TYPE)

        val userData = Bundle()
        with(userData) {
            putString(UID, user.uid)
            putString(NAME, user.name)
            putString(PHONE, user.phone)
        }

        accountManager.addAccountExplicitly(account, user.email, userData)
        accountManager.setAuthToken(account, AccountAuthenticator.ARG_AUTH_TYPE, user.token)
    }

    fun logout() {
        if (accounts.isEmpty()) return

        val token = getToken(accounts.get(0))
        if (token == null) return

        accountManager.invalidateAuthToken(AccountAuthenticator.ARG_ACCOUNT_TYPE, token)
    }

    fun getUser(): Login {
        if (accounts.isEmpty()) return Login()

        return Login(
                email = accountManager.getPassword(accounts.get(0)),
                uid = accountManager.getUserData(accounts.get(0), UID),
                name = accountManager.getUserData(accounts.get(0), NAME),
                phone = accountManager.getUserData(accounts.get(0), PHONE)
        )
    }

    fun getEmail(): String {
        if (accounts.isEmpty()) return ""

        return accountManager.getPassword(accounts.get(0))
    }

    private fun getToken(account: Account): String? {
        val future = accountManager.getAuthToken(
                account,
                AccountAuthenticator.ARG_AUTH_TYPE,
                null,
                false,
                null,
                null)

        var token: String? = null
        val deferred = launch(CommonPool) { token = suspendExecute(future) }
        launch(UI) { deferred.join() }
        return token
    }

    suspend private fun suspendExecute(future: AccountManagerFuture<Bundle>): String? {
        try {
            val result = future.getResult(500, TimeUnit.MILLISECONDS)
            return result.getString(AccountManager.KEY_AUTHTOKEN)
        } catch (e: Exception ){
            return ""
        }
    }



}