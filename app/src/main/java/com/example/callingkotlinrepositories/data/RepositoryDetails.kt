package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryDetails(
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("size") val size: Int = 0,
    @field:SerializedName("stargazers_count") val stargazersCount: Int = 0,
    @field:SerializedName("watchers_count") val watchersCount: Int = 0,
    @field:SerializedName("forks_count") val forksCount: Int = 0,
    @field:SerializedName("open_issues_count") val openIssuesCount: Int = 0,
    val issuesOverPastYear: Int = 0
)