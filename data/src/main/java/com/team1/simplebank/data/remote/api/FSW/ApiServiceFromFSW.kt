package com.team1.simplebank.data.remote.api.FSW

import com.team1.simplebank.data.remote.request.FSW.BankValidationRequest
import com.team1.simplebank.data.remote.response.FSW.BankValidationResponse
import com.team1.simplebank.data.remote.response.FSW.TypeAccount
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceFromFSW {

    @POST("v1/transfer/validation/bank")
    suspend fun bankValidation(
        @Body requestBody: BankValidationRequest,
    ): BankValidationResponse

    @GET("v1/account/type/accountTypes")
    suspend fun getAccountType(): TypeAccount
}