package com.example.callingkotlinrepositories.loginuser

import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.User
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginUserRemoteDataSourceImpl(private val gitHubApiService: GitHubApiService) :
    LoginUserRemoteDataSource {

    private val credentialError = SingleLiveData<Any>()

    override fun getRemoteUserDetails(loginUserCallback: LoginUserCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = gitHubApiService.logInGetUserDetails()
            withContext(Dispatchers.Main) {
                try {
                    response.enqueue(object : Callback<User> {
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure message: ${t.message}")
                            loginUserCallback.userCallBack(null)
                        }

                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (!response.isSuccessful) {
                                Logger.e(KOIN_TAG, "onResponse error code: ${response.code()}")
                                credentialError.call()
                                loginUserCallback.userCallBack(null)
                            }

                            if (response.isSuccessful) {
                                Logger.d(KOIN_TAG, "onResponse is successful!")
                                loginUserCallback.userCallBack(response.body())
                            }
                        }
                    })
                } catch (e: Exception) {
                    Logger.d(KOIN_TAG, "Error getting response, see message: ${e.message}")
                    loginUserCallback.userCallBack(null)
                }
            }
        }
    }

    override val mutableCredentialError: SingleLiveData<Any>
        get() = credentialError
}