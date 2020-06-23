package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryIssuesCounter(
    @field:SerializedName("total_count") val total_count: Int = 0) {

    override fun toString(): String {
        return "RepositoryIssuesCounter: total_count=$total_count"
    }
}