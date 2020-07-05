package com.example.callingkotlinrepositories.data

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(description)
        writeInt(size)
        writeInt(stargazers_count)
        writeInt(watchers_count)
        writeInt(forks_count)
        writeInt(open_issues_count)
    }

    override fun toString(): String {
        return "RepositoryDetails: id:$id, name:$name, description:$description, size:$size, stargazers count:$stargazers_count, watchers count: $watchers_count," +
                "forks count:$forks_count, open issues count:$open_issues_count"
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RepositoryDetails> {
        override fun createFromParcel(parcel: Parcel): RepositoryDetails {
            return RepositoryDetails(parcel)
        }

        override fun newArray(size: Int): Array<RepositoryDetails?> {
            return arrayOfNulls(size)
        }
    }
}