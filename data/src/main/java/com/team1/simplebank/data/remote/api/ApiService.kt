package com.team1.simplebank.data.remote.api

import com.team1.simplebank.data.remote.request.LoginRequest
import com.team1.simplebank.data.remote.response.AccountResponse
import com.team1.simplebank.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("v1/auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ) : LoginResponse

    @GET("v1/accounts")
    suspend fun getAccounts(): AccountResponse

}