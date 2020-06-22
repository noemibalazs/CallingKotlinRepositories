package com.example.callingkotlinrepositories.loginuser

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.base.BaseViewModel
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.User
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginUserViewModel(private val gitHubApiService: GitHubApiService) : BaseViewModel() {

    val userName = ObservableField("")
    private val _mutableUserName = SingleLiveData<String>()
    val mutableUserName: LiveData<String>
        get() = _mutableUserName

    val userPassword = ObservableField("")
    private val _mutableUserPassword = SingleLiveData<String>()
    val mutableUserPassword: LiveData<String>
        get() = _mutableUserPassword

    private val _mutableUserDetails: SingleLiveData<User> = SingleLiveData()
    val mutableUserDetails: LiveData<User?>
        get() = _mutableUserDetails

    val mutableErrorField = SingleLiveData<Any>()
    val mutableCredentialError = SingleLiveData<Any>()

    fun onSignInClicked() {
        Logger.d(KOIN_TAG, "onSignIn was clicked")

        setUserName(userName)
        setUserPassword(userPassword)

        if (!userName.get().isNullOrEmpty() and !userPassword.get().isNullOrEmpty()) {
            getUserDetails()
        }
    }

    private fun setUserName(userName: ObservableField<String>) {
        Logger.d(KOIN_TAG, "observedField user name: $userName")

        if (!userName.get().isNullOrEmpty()) {
            _mutableUserName.value = userName.get()
        } else {
            userName.set("")
            mutableErrorField.call()
        }
    }

    private fun setUserPassword(userPassword: ObservableField<String>) {
        Logger.d(KOIN_TAG, "observedField user password: $userPassword")

        if (!userPassword.get().isNullOrEmpty()) {
            _mutableUserPassword.value = userPassword.get()
        } else {
            userPassword.set("")
            mutableErrorField.call()
        }
    }

    private fun getUserDetails() {
        val job = launch {
            val result = gitHubApiService.logInGetUserDetails()
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<User> {
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure message: ${t.message}")
                        }

                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (!response.isSuccessful) {
                                Logger.e(KOIN_TAG, "onResponse error code: ${response.code()}")
                                mutableCredentialError.call()
                            }

                            if (response.isSuccessful) {
                                Logger.d(KOIN_TAG, "onResponse is successful!")
                                _mutableUserDetails.value = response.body()
                            }
                        }
                    })
                } catch (e:Exception){
                    Logger.d(KOIN_TAG, "Error getting response, see message: ${e.message}")
                }
            }
        }

        addJob(job)
    }
}