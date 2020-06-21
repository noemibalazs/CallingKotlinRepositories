package com.example.callingkotlinrepositories.helper

import android.content.Context
import com.example.callingkotlinrepositories.utils.*

class DataManager(
    private val context: Context
) {

    private val sharedPreferences =
        context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE)

    fun saveUserNameOrEmail(nameOrEmail: String) {
        sharedPreferences.edit().putString(USER_NAME_OR_EMAIL, nameOrEmail).apply()
    }

    fun getUserNameOrEmail(): String {
        return sharedPreferences.getString(USER_NAME_OR_EMAIL, "") ?: ""
    }

    fun saveUserPassword(password: String) {
        sharedPreferences.edit().putString(USER_PASSWORD, password).apply()
    }

    fun getUserPassword(): String {
        return sharedPreferences.getString(USER_PASSWORD, "") ?: ""
    }

    fun saveUserAvatarUrl(avatarUrl: String) {
        sharedPreferences.edit().putString(USER_AVATAR, avatarUrl).apply()
    }

    fun getUserAvatarUrl(): String {
        return sharedPreferences.getString(USER_AVATAR, "") ?: ""
    }

    fun saveUserLoginAs(loginAs: String) {
        sharedPreferences.edit().putString(USER_LOGIN_AS, loginAs).apply()
    }

    fun getUserLoginAs(): String {
        return sharedPreferences.getString(USER_LOGIN_AS, "") ?: ""
    }
}