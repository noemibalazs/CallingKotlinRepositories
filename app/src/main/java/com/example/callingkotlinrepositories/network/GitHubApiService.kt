package com.example.callingkotlinrepositories.network

import com.example.callingkotlinrepositories.data.*
import com.example.callingkotlinrepositories.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubApiService {

    @GET("user")
    fun logInGetUserDetails(): Call<User>

    @GET("search/repositories?page=1&per_page=50&q=language:kotlin&sort=starts")
    fun getRepositoryList(): Call<KotlinRepositories>

    @GET("repositories/{id}")
    fun getRepositoryDetails(@Path("id") id: Int): Call<RepositoryDetails>

    @GET("search/issues?")
    fun getRepositoryIssues(
        @Query("q") queryParameter: String,
        @Query("created") created: String,
        @Query("type") type: String
    ): Call<RepositoryIssuesCounter>

    @GET("repos/{name}/{repo}/stats/commit_activity")
    fun getRepositoryLastYearStats(@Path("name") name: String, @Path("repo") repo:String): Call<MutableList<LastYearStats>>

    companion object {

        fun getGitHubApiService(headerInterceptor: HeaderInterceptor): GitHubApiService {

            val interceptor =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(headerInterceptor)
                .build()
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(GitHubApiService::class.java)
        }
    }
}