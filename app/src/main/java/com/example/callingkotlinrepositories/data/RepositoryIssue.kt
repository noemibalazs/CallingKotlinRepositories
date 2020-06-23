package com.example.callingkotlinrepositories.data

data class RepositoryIssue(
    val count: Int = 0
) {
    override fun toString(): String {
        return "RepositoryIssue: count -$count"
    }
}