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

class LoginUserViewModel(private val loginUserRemoteDataSource: LoginUserRemoteDataSource): BaseViewModel() {

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
    val mutableCredentialError = loginUserRemoteDataSource.mutableCredentialError

    private val loginUserCallback = object : LoginUserCallback{
        override fun userCallBack(user: User?) {
            _mutableUserDetails.value = user
        }
    }

    fun onSignInClicked() {
        Logger.d(KOIN_TAG, "onSignIn was clicked")

        setUserName(userName)
        setUserPassword(userPassword)

        if (!userName.get().isNullOrEmpty() and !userPassword.get().isNullOrEmpty()) {
            loginUserRemoteDataSource.getRemoteUserDetails(loginUserCallback)
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
}