package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("login") val login: String? = null,
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("avatar_url") val avatar_url: String? = null
) {
    override fun toString(): String {
        return "User: login: $login, id: $id, avatar_url: $avatar_url"
    }
}