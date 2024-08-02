package com.team1.simplebank.data.remote.api

import com.team1.simplebank.data.remote.request.LoginRequest
import com.team1.simplebank.data.remote.response.AccountResponse
import com.team1.simplebank.data.remote.response.LoginResponse
import com.team1.simplebank.data.remote.response.MutationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @POST("v1/auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("v1/accounts")
    suspend fun getAccounts(): AccountResponse

    @GET("v1/mutations/{noAccount}")
    suspend fun getMutations(
        @Path("noAccount") noAccount: String = "3737657598213562",
        @Query("month") month: Int,
        @Query("type") type: String?=null,
        @Query("page") page: Int? = 0,
        @Query("size") size: Int? = 10
    ): MutationResponse

}