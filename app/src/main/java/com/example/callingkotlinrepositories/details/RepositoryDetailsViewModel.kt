package com.example.callingkotlinrepositories.details

import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.base.BaseViewModel
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsViewModel(
    private val gitHubApiService: GitHubApiService,
    private val dataManager: DataManager
) : BaseViewModel() {

    private var _mutableRepositoryDetails = SingleLiveData<RepositoryDetails>()
    val mutableRepositoryDetails: LiveData<RepositoryDetails>
        get() = _mutableRepositoryDetails

    val mutableFailureError = SingleLiveData<Any>()

    init {
        getRepositoryDetails()
    }

    private fun getRepositoryDetails() {
        val job = launch {
            val result = gitHubApiService.getRepositoryDetails(dataManager.getRepositoryId())
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<RepositoryDetails> {
                        override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure message: ${t.message}")
                        }

                        override fun onResponse(
                            call: Call<RepositoryDetails>,
                            response: Response<RepositoryDetails>
                        ) {
                            if (!response.isSuccessful) {
                                Logger.e(KOIN_TAG, "onResponse failure code: ${response.code()}")
                                mutableFailureError.call()
                            }

                            if (response.isSuccessful) {
                                _mutableRepositoryDetails.value = response.body()!!
                            }
                        }
                    })
                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "Something went wrong, see message: ${e.message}")
                }
            }
        }

        addJob(job)
    }

}