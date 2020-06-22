package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class KotlinRepositories(
    @field:SerializedName("total_count") val totalCount: Int = 0,
    @field:SerializedName("items") val items: MutableList<Repository>
) {

    override fun toString(): String {
        return "KotlinRepositories total_count: $totalCount, items: ${items.size}"
    }
}