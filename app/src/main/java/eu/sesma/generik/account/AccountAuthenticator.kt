package eu.sesma.generik.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import eu.sesma.generik.BuildConfig
import eu.sesma.generik.ui.login.LoginActivity
import javax.inject.Inject


class AccountAuthenticator
@Inject constructor(
        val context: Context
) : AbstractAccountAuthenticator(context) {

    companion object {
        val ARG_ACCOUNT_TYPE = BuildConfig.APPLICATION_ID //Ensure this is equal to @string/account_type
        val ARG_AUTH_TYPE = "full_access"
    }

    override fun getAuthTokenLabel(p0: String?): String {
        return ""
    }

    override fun confirmCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Bundle?): Bundle {
        return Bundle()
    }

    override fun updateCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: String?, p3: Bundle?): Bundle {
        return Bundle()
    }

    override fun getAuthToken(
            response: AccountAuthenticatorResponse,
            account: Account,
            authTokenType: String,
            options: Bundle?): Bundle {
        val accountManager = AccountManager.get(context)

        val authToken = accountManager.peekAuthToken(account, authTokenType)

        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        return addAccount(response, account.type, authTokenType, emptyArray(), Bundle())
    }

    override fun hasFeatures(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Array<out String>?): Bundle {
        return Bundle()
    }

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle {
        return Bundle()
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
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return Bundle()
    }
}