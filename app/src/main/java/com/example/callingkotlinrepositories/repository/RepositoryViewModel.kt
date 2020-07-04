package com.example.callingkotlinrepositories.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.callingkotlinrepositories.base.BaseViewModel
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.utils.REPOSITORY_LIST_SAVED_STATE

class RepositoryViewModel(
    val handle: SavedStateHandle,
    private val repositoryRemoteDataSource: RepositoryRemoteDataSource
) : BaseViewModel() {

    val mutableFailureError = repositoryRemoteDataSource.mutableFailureError

    val showLoading = repositoryRemoteDataSource.showLoading

    fun saveRepositoryListDueConfChanges(repos: MutableList<Repository>) {
        handle.set(REPOSITORY_LIST_SAVED_STATE, repos)
    }

    fun getReposListAfterConfChanges(): LiveData<MutableList<Repository>> {
        return handle.getLiveData(REPOSITORY_LIST_SAVED_STATE)
    }

    fun getRepositoryList(): LiveData<MutableList<Repository>> = repositoryRemoteDataSource.getRemoteRepositoryList()
}