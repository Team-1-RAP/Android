package com.team1.simplebank.di

import com.team1.simplebank.data.BuildConfig
import com.team1.simplebank.data.dataStore.Auth
import com.team1.simplebank.data.remote.api.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideApiService(get()) }
}

fun provideApiService(auth: Auth): ApiService {
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val authInterceptor = Interceptor {
        val token = runBlocking {
            auth.getUserSession().first().accessToken
        }
        val requestHeaders = it.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        it.proceed(requestHeaders)
    }

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    return retrofit.create(ApiService::class.java)
}

