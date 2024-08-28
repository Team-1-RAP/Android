package com.team1.simplebank.data.remote.request.FSW

data class ResetPwdOtpValidationRequest(
    val atm_card_no: String,
    val otp: String
)
