package com.example.callingkotlinrepositories.details

import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.LastYearStats
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.data.RepositoryIssuesCounter

interface RepositoryDetailsRemoteDataSource {

    fun getRemoteRepositoryDetails(): LiveData<RepositoryDetails>

    fun getRemoteRepositoryIssueCounter(): LiveData<RepositoryIssuesCounter>

    fun getRemoteRepositoryLastYearStats(): LiveData<MutableList<LastYearStats>>

    val mutableFailureError: SingleLiveData<Any>
}