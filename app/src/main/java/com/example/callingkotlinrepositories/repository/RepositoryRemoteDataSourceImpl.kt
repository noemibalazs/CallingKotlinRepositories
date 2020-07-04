package com.example.callingkotlinrepositories.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.KotlinRepositories
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RepositoryRemoteDataSourceImpl(private val gitHubApiService: GitHubApiService) :
    RepositoryRemoteDataSource {

    private val failureError =  SingleLiveData<Any>()
    private val loading = SingleLiveData<Boolean>()

    override fun getRemoteRepositoryList(): LiveData<MutableList<Repository>> {
        val mutableRepositoryList = MutableLiveData<MutableList<Repository>>()
        loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val result = gitHubApiService.getRepositoryList()
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<KotlinRepositories> {

                        override fun onFailure(call: Call<KotlinRepositories>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure error message: ${t.localizedMessage} and ${t.message}")
                            loading.value = false
                        }

                        override fun onResponse(call: Call<KotlinRepositories>, response: Response<KotlinRepositories>) {

                            loading.value = false

                            if (!response.isSuccessful) {
                                Logger.e(KOIN_TAG, "onResponse error, see error code: ${response.code()}")
                                failureError.call()
                            }

                            if (response.isSuccessful) {
                                Logger.d(KOIN_TAG, "onResponse success, see response body size: ${response.body()?.items?.size}}")
                                mutableRepositoryList.value = response.body()?.items
                            }
                        }
                    })

                } catch (e: Exception) {
                    Logger.d(KOIN_TAG, "Something went wrong, see error message: ${e.message}")
                }
            }
        }
        return mutableRepositoryList
    }

    override val mutableFailureError: SingleLiveData<Any>
        get() = failureError

    override val showLoading: SingleLiveData<Boolean>
        get() = loading
}