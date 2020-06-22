package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class KotlinRepositories(
    @field:SerializedName("total_count") val total_count: Int = 0,
    @field:SerializedName("items") val items: MutableList<Repository>
) {

    override fun toString(): String {
        return "KotlinRepositories total_count: $total_count, items: ${items.size}"
    }
}