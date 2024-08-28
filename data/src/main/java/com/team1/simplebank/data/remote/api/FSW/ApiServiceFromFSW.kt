package com.team1.simplebank.data.remote.api.FSW

import com.team1.simplebank.data.remote.request.FSW.BankValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdBirthDateValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdCardValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdChangeValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdEmailValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdOtpValidationRequest
import com.team1.simplebank.data.remote.request.FSW.ResetPwdPinValidationRequest
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

    @POST("v1/reset/password/validation/card")
    suspend fun resetPwdCardValidation(
        @Body requestBody: ResetPwdCardValidationRequest,
    )

    @POST("v1/reset/password/validation/birthDate")
    suspend fun resetPwdBirthDateValidation(
        @Body requestBody: ResetPwdBirthDateValidationRequest,
    )

    @POST("v1/reset/password/validation/email")
    suspend fun resetPwdEmailValidation(
        @Body requestBody: ResetPwdEmailValidationRequest,
    )

    @POST("v1/reset/password/validation/otpVerify")
    suspend fun resetPwdOtpValidation(
        @Body requestBody: ResetPwdOtpValidationRequest,
    )

    @POST("v1/reset/password/validation/changePassword")
    suspend fun resetPwdChangeValidation(
        @Body requestBody: ResetPwdChangeValidationRequest,
    )
    @POST("v1/reset/password/validation/pin")
    suspend fun resetPwdPinValidation(
        @Body requestBody: ResetPwdPinValidationRequest,
    )
}