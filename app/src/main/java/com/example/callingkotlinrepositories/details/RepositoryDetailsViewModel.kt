package com.example.callingkotlinrepositories.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.base.BaseViewModel
import com.example.callingkotlinrepositories.base.SingleLiveData
import com.example.callingkotlinrepositories.data.LastYearStats
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.data.RepositoryIssuesCounter
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.example.callingkotlinrepositories.utils.*
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
    private val dataManager: DataManager,
    private val application: Application
) : BaseViewModel() {

    private var _mutableRepositoryDetails = SingleLiveData<RepositoryDetails>()
    val mutableRepositoryDetails: LiveData<RepositoryDetails>
        get() = _mutableRepositoryDetails

    val mutableFailureError = SingleLiveData<Any>()

    private var _mutableRepositoryIssues = SingleLiveData<RepositoryIssuesCounter>()
    val mutableRepositoryIssues: LiveData<RepositoryIssuesCounter>
        get() = _mutableRepositoryIssues


    private var _mutableRepositoryLastYearStats = SingleLiveData<MutableList<LastYearStats>>()
    val mutableRepositoryLastYearStats: LiveData<MutableList<LastYearStats>>
        get() = _mutableRepositoryLastYearStats

    init {
        getRepositoryDetails()
        getRepositoryIssuesCount()
        getRepositoryStats()
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

    private fun getRepositoryIssuesCount() {
        val job = launch {
            val queryParam =
                application.getString(R.string.query_param, dataManager.getRepositoryFullName())
            val result = gitHubApiService.getRepositoryIssues(queryParam, QUERY_TYPE, QUERY_ISSUE)
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<RepositoryIssuesCounter> {
                        override fun onFailure(call: Call<RepositoryIssuesCounter>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure error message: ${t.message}")
                        }

                        override fun onResponse(
                            call: Call<RepositoryIssuesCounter>,
                            response: Response<RepositoryIssuesCounter>
                        ) {
                            if (!response.isSuccessful) {
                                Log.e(KOIN_TAG, "onResponse failure code: ${response.code()}")
                                mutableFailureError.call()
                            }

                            if (response.isSuccessful) {
                                Logger.d(KOIN_TAG, "onResponse successful")
                                _mutableRepositoryIssues.value = response.body()
                            }
                        }
                    })

                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "Something went wrong!")
                }
            }
        }
        addJob(job)
    }

    private fun getRepositoryStats() {
        val job = launch {
            val fullName = dataManager.getRepositoryFullName().split("/")
            val result =
                gitHubApiService.getRepositoryLastYearStats(fullName[0], fullName[1])
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object:Callback<MutableList<LastYearStats>>{
                        override fun onFailure(call: Call<MutableList<LastYearStats>>, t: Throwable) {
                            Logger.e(KOIN_TAG, "onFailure message: ${t.message}")
                        }

                        override fun onResponse(call: Call<MutableList<LastYearStats>>, response: Response<MutableList<LastYearStats>>) {
                            if (!response.isSuccessful){
                                Logger.e(KOIN_TAG, "onFailure code: ${response.code()}")
                                mutableFailureError.call()
                            }

                            if (response.isSuccessful){
                                _mutableRepositoryLastYearStats.value = response.body()
                            }
                        }
                    })

                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "Something went wrong!")
                }
            }
        }
        addJob(job)
    }
}