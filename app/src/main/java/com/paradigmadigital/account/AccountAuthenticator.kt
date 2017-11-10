package com.paradigmadigital.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.paradigmadigital.BuildConfig
import com.paradigmadigital.ui.login.LoginActivity
import javax.inject.Inject


class AccountAuthenticator
@Inject constructor(
        val context: Context
) : AbstractAccountAuthenticator(context) {

    companion object {
        val ARG_ACCOUNT_TYPE = BuildConfig.APPLICATION_ID //Ensure this is equal to @string/account_type
        val ARG_AUTH_TYPE = "full_access"
        val ARG_IS_ADDING_NEW_ACCOUNT = "is_adding_new_account"
    }

    override fun getAuthTokenLabel(p0: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Bundle?): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: String?, p3: Bundle?): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAuthToken(
            response: AccountAuthenticatorResponse,
            account: Account,
            authTokenType: String,
            options: Bundle?): Bundle {
        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        val accountManager = AccountManager.get(context)

        val authToken = accountManager.peekAuthToken(account, authTokenType)

//        // Lets give another try to authenticate the user
//        if (TextUtils.isEmpty(authToken)) {
//            val password = accountManager.getPassword(account)
//            if (password != null) {
//                authToken = sServerAuthenticate.userSignIn(account.name, password, authTokenType)
//            }
//        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        return addAccount(response, account.type, authTokenType, emptyArray(), Bundle())
    }

    override fun hasFeatures(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Array<out String>?): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAccount(
            response: AccountAuthenticatorResponse?,
            accountType: String?,
            authTokenType: String?,
            requiredFeatures: Array<out String>?,
            options: Bundle?): Bundle {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(ARG_ACCOUNT_TYPE, accountType)
        intent.putExtra(ARG_AUTH_TYPE, authTokenType)
//        intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

}