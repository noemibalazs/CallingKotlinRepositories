package com.example.callingkotlinrepositories.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), KoinComponent, CoroutineScope {

    val jobs: ArrayList<Job> = arrayListOf()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun addJob(job: Job) {
        if (!jobs.contains(job))
            jobs.add(job)
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { job ->
            job.apply {
                if (!job.isCancelled and (job.isActive or !job.isCompleted))
                    job.cancel()
            }
        }
    }
}