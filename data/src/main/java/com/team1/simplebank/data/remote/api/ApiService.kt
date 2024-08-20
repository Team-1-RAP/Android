package com.team1.simplebank.data.remote.api

import com.team1.simplebank.data.remote.request.ConfirmQrisMerchantRequest
import com.team1.simplebank.data.remote.request.ConfirmQrisReceiveFundsRequest
import com.team1.simplebank.data.remote.request.GenerateQrisCodeRequest
import com.team1.simplebank.data.remote.request.LoginRequest
import com.team1.simplebank.data.remote.response.AccountResponse
import com.team1.simplebank.data.remote.response.GetAmountsMutation
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
        @Path("noAccount") noAccount: String,
        @Query("month") month: Int,
        @Query("type") type: String? = null,
        @Query("page") page: Int? = 0,
        @Query("size") size: Int? = 10
    ): MutationResponse

    @GET("v1/mutations/{noAccount}/amounts")
    suspend fun getMutationsAmount(
        @Path("noAccount") noAccount: String,
    ): GetAmountsMutation

    // QRIS
    @GET("v1/qris/scan-qris/{qrCode}")
    suspend fun scanQris(
        @Path("qrCode") qrCode: String
    )

    @GET("v1/qris/validate-qr-code/{qrCode}")
    suspend fun getQrisStatus(
        @Path("qrCode") qrCode: String
    )

    @POST("v1/qris/confirm-qris-merchant")
    suspend fun confirmQrisMerhant(
        @Body request : ConfirmQrisMerchantRequest
    )

    @POST("v1/qris/confirm-qris-receives")
    suspend fun confirmQrisReceivesFUnds(
        @Body request : ConfirmQrisReceiveFundsRequest
    )

    @POST("v1/qris/generate-qr-code")
    suspend fun generateQrisCode(
        @Body request : GenerateQrisCodeRequest
    )

}