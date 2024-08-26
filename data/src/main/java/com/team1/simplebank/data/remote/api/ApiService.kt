package com.team1.simplebank.data.remote.api

import com.team1.simplebank.data.remote.request.BEJ.LoginRequest
import com.team1.simplebank.data.remote.request.BEJ.TransferRequest
import com.team1.simplebank.data.remote.response.BEJ.AccountResponse
import com.team1.simplebank.data.remote.response.BEJ.GetAllBankResponse
import com.team1.simplebank.data.remote.response.BEJ.GetAmountsMutation
import com.team1.simplebank.data.remote.response.BEJ.LoginResponse
import com.team1.simplebank.data.remote.response.BEJ.MutationResponse
import com.team1.simplebank.data.remote.response.BEJ.ResultTransferResponse
import com.team1.simplebank.data.remote.request.ConfirmQrisMerchantRequest
import com.team1.simplebank.data.remote.request.ConfirmQrisReceiveFundsRequest
import com.team1.simplebank.data.remote.request.GenerateQrisCodeRequest
import com.team1.simplebank.data.remote.response.ConfirmQrisTransactionResponse
import com.team1.simplebank.data.remote.response.GenerateQrisCodeResponse
import com.team1.simplebank.data.remote.response.GetQrisStatusResponse
import com.team1.simplebank.data.remote.response.ScanQrisResponse
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

    @GET("v1/banks")
    suspend fun getAllBank(): GetAllBankResponse

    @POST("v1/bank-transfers")
    suspend fun transfer(
        @Body transferRequest: TransferRequest
    ): ResultTransferResponse

    // QRIS
    @GET("v1/qris/scan-qris/{qrCode}")
    suspend fun scanQris(
        @Path("qrCode") qrCode: String
    ) : ScanQrisResponse

    @GET("v1/qris/validate-qr-code/{qrCode}")
    suspend fun getQrCodeStatus(
        @Path("qrCode") qrCode: String
    ) : GetQrisStatusResponse

    @POST("v1/qris/confirm-qris-merchant")
    suspend fun confirmQrisMerhant(
        @Body request : ConfirmQrisMerchantRequest
    ) : ConfirmQrisTransactionResponse

    @POST("v1/qris/confirm-qris-receives")
    suspend fun confirmQrisReceivesFunds(
        @Body request : ConfirmQrisReceiveFundsRequest
    ): ConfirmQrisTransactionResponse

    @POST("v1/qris/generate-qr-code")
    suspend fun generateQrisCode(
        @Body request : GenerateQrisCodeRequest
    ): GenerateQrisCodeResponse

}