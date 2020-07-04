package com.example.callingkotlinrepositories.repository

import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.Repository

interface RepositoryRemoteDataSource {

    fun getRemoteRepositoryList(): LiveData<MutableList<Repository>>

    val mutableFailureError: SingleLiveData<Any>

    val showLoading : SingleLiveData<Boolean>
}