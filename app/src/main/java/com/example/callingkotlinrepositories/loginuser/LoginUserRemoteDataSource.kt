package com.example.callingkotlinrepositories.loginuser

import com.example.callingkotlinrepositories.base.SingleLiveData

interface LoginUserRemoteDataSource {

    fun getRemoteUserDetails(loginUserCallback: LoginUserCallback)

    val mutableCredentialError: SingleLiveData<Any>
}