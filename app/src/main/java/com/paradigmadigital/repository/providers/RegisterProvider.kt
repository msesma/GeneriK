package com.paradigmadigital.repository.providers

import android.os.SystemClock
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.SecurePreferences
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.Executor
import javax.inject.Inject


class RegisterProvider
@Inject
constructor(
        private val retrofit: Retrofit,
        private val executor: Executor,
        private val securePreferences: SecurePreferences,
        private val userDao: UserDao
) {
    fun registerUser(user: User, pass: String, callback: CallbackFun<NetworkResult>) {
        executor.execute {
            try {
//                val service = retrofit.create(LoginRegisterService::class.java)


                SystemClock.sleep(1000) //TODO: call register service
//                val response = service.registerUser("").execute()
//                val body = response.body()
//                if (body == null) {
//                    callback(NetworkResult.DISCONNECTED)
//                    return@execute
//                }
                securePreferences.password = pass
                userDao.insert(user)
                callback(NetworkResult.SUCCESS)
            } catch (e: Throwable) {
                when (e) {
                    is UnknownHostException -> callback(NetworkResult.DISCONNECTED)
                    is IllegalArgumentException -> callback(NetworkResult.BAD_URL)
                    else -> callback(NetworkResult.UNKNOWN)
                }
            }
        }
    }
}