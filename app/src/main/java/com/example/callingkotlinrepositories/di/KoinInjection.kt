package com.example.callingkotlinrepositories.di

import org.koin.core.module.Module

class KoinInjection {

    companion object {

        fun getModules(): MutableList<Module> {

            fun getDataManagerModule() = listOf(dataManagerModule)
            fun getHeaderInterceptorModule() = listOf(headerInterceptorModule)
            fun getNetworkModule() = listOf(networkModule)
            fun getUserViewModule() = listOf(userViewModelModule)
            fun getRepositoryViewModelModule() = listOf(repositoryViewModelModule)

            return mutableListOf<Module>().apply {
                addAll(getDataManagerModule())
                addAll(getHeaderInterceptorModule())
                addAll(getNetworkModule())
                addAll(getUserViewModule())
                addAll(getRepositoryViewModelModule())
            }
        }
    }
}