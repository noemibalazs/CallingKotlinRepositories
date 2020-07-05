package com.example.callingkotlinrepositories.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RepositoryIssuesCounter(
    @field:SerializedName("total_count") val total_count: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(total_count)
    }

    override fun toString(): String {
        return "RepositoryIssuesCounter: total_count=$total_count"
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RepositoryIssuesCounter> {
        override fun createFromParcel(parcel: Parcel): RepositoryIssuesCounter {
            return RepositoryIssuesCounter(parcel)
        }

        override fun newArray(size: Int): Array<RepositoryIssuesCounter?> {
            return arrayOfNulls(size)
        }
    }
}