package com.example.callingkotlinrepositories.network

import com.example.callingkotlinrepositories.helper.DataManager
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class HeaderInterceptor(private val dataManager: DataManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = "${dataManager.getUserNameOrEmail()}:${dataManager.getUserPassword()}"
        val encodedCredentials = Base64.getEncoder().encodeToString(credentials.toByteArray())
        val token = "Basic $encodedCredentials"
        val request = chain.request()
            .newBuilder().addHeader(
                "Authorization", token
            ).build()
        return chain.proceed(request)
    }
}