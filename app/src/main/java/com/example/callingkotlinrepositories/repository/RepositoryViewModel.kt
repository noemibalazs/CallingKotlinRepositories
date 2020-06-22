package com.example.callingkotlinrepositories.repository

import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.base.BaseViewModel
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.KotlinRepositories
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(private val gitHubApiService: GitHubApiService) : BaseViewModel() {

    private val _mutableRepositoryList = SingleLiveData<MutableList<Repository>>()
    val mutableRepositoryList: LiveData<MutableList<Repository>?>
        get() = _mutableRepositoryList

    val mutableFailureError = SingleLiveData<Any>()

    init {
        getRepositoryList()
    }

    private fun getRepositoryList() {
        val job = launch {
            val result = gitHubApiService.getRepositoryList()
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<KotlinRepositories> {
                        override fun onFailure(call: Call<KotlinRepositories>, t: Throwable) {
                            Logger.e(
                                KOIN_TAG,
                                "onFailure error message: ${t.localizedMessage} and ${t.message}"
                            )
                        }

                        override fun onResponse(
                            call: Call<KotlinRepositories>,
                            response: Response<KotlinRepositories>
                        ) {
                            if (!response.isSuccessful) {
                                Logger.e(
                                    KOIN_TAG,
                                    "onResponse error, see error code: ${response.code()}"
                                )
                                mutableFailureError.call()
                            }

                            if (response.isSuccessful) {
                                Logger.d(
                                    KOIN_TAG,
                                    "onResponse success, see response body size: ${response.body()?.items?.size}}"
                                )
                                _mutableRepositoryList.value = response.body()?.items
                            }
                        }
                    })

                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "Error getting response, see message: ${e.message}")
                }
            }
        }

        addJob(job)
    }

}