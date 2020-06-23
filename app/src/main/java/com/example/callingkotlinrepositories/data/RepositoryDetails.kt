package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryDetails(
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("size") val size: Int = 0,
    @field:SerializedName("stargazers_count") val stargazers_count: Int = 0,
    @field:SerializedName("watchers_count") val watchers_count: Int = 0,
    @field:SerializedName("forks_count") val forks_count: Int = 0,
    @field:SerializedName("open_issues_count") val open_issues_count: Int = 0
) {
    override fun toString(): String {
        return "RepositoryDetails: id:$id, name:$name, description:$description, size:$size, stargazers count:$stargazers_count, watchers count: $watchers_count," +
                "forks count:$forks_count, open issues count:$open_issues_count"
    }
}