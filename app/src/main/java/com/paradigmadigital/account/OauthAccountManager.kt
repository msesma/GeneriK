package com.paradigmadigital.account

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerFuture
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.paradigmadigital.api.model.Login
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class OauthAccountManager
@Inject constructor(
        val context: Context
) {

    companion object {
        private val TAG = OauthAccountManager::class.simpleName
    }

    val accountManager = AccountManager.get(context)
    val accounts
        get() = accountManager.getAccountsByType(AccountAuthenticator.ARG_ACCOUNT_TYPE)

    fun isLoggedIn(): Boolean {
        if (accounts.isEmpty()) return false

        val token = getToken(accounts.get(0))
        Log.d(TAG, "Is logged in, Token: " + token)
        return !token.isNullOrEmpty()
    }

    fun addAccount(user: Login) {
        Log.d(TAG, "Add account")
        //The account name will be the app package, we will use the password field for the user email
        val account = Account(
                AccountAuthenticator.ARG_ACCOUNT_TYPE,
                AccountAuthenticator.ARG_ACCOUNT_TYPE)

        accountManager.addAccountExplicitly(account, user.email, null)
        accountManager.setAuthToken(account, AccountAuthenticator.ARG_AUTH_TYPE, user.token)
    }

    fun logout() {
        Log.d(TAG, "Logout")
        if (accounts.isEmpty()) return

        val token = getToken(accounts.get(0))
        if (token == null) return

        accountManager.invalidateAuthToken(AccountAuthenticator.ARG_ACCOUNT_TYPE, token)
    }

    fun getEmail(): String {
        if (accounts.isEmpty()) return ""

        return accountManager.getPassword(accounts.get(0))
    }

    fun getAuthToken(): String {
        if (accounts.isEmpty()) return ""

        val token = getToken(accounts.get(0))
        return token ?: ""
    }

    fun refreshToken(newToken: String) {
        if (accounts.isEmpty()) return

        accountManager.setAuthToken(accounts.get(0), AccountAuthenticator.ARG_AUTH_TYPE, newToken)
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
        runBlocking {
            token = async(CommonPool) { suspendExecute(future) }.await()
        }
        return token
    }

    suspend private fun suspendExecute(future: AccountManagerFuture<Bundle>): String? {
        try {
            val result = future.getResult(100, TimeUnit.MILLISECONDS)
            return result.getString(AccountManager.KEY_AUTHTOKEN)
        } catch (e: Exception) {
            return ""
        }
    }

}