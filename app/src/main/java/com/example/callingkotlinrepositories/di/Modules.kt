package com.example.callingkotlinrepositories.di

import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.network.GitHubApiService
import com.example.callingkotlinrepositories.network.HeaderInterceptor
import com.example.callingkotlinrepositories.loginuser.LoginUserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataManagerModule = module {
    single { DataManager(androidApplication().applicationContext) }
}

val headerInterceptorModule = module {
    single { HeaderInterceptor(dataManager = get()) }
}

val networkModule = module {
    single { GitHubApiService.getGitHubApiService(headerInterceptor = get()) }
}

val userViewModule = module {
    viewModel { LoginUserViewModel(gitHubApiService = get()) }
}