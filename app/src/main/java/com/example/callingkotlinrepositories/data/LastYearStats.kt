package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class LastYearStats(
    @field:SerializedName("total") val total: Int = 0,
    @field:SerializedName("week") val week: Long = 0,
    @field:SerializedName("days") val days: MutableList<Int>
){
    override fun toString(): String {
        return "LastYearStats: total=$total, week=$week, days=$days"
    }
}