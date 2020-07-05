package com.example.callingkotlinrepositories.loginuser

import com.example.callingkotlinrepositories.data.User

interface LoginUserCallback {

    fun userCallBack(user: User?)
}