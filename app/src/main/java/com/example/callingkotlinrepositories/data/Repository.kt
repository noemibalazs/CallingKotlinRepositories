package com.example.callingkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class Repository(
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("full_name") val fullName: String? = null,
    @field:SerializedName("description") val description: String? = null
) {
    override fun toString(): String {
        return "Repository: id = $id, name = $name, full_name = $fullName, description = $description"
    }
}