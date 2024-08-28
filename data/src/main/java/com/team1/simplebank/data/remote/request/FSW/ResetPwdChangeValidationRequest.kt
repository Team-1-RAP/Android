package com.team1.simplebank.data.remote.request.FSW

data class ResetPwdChangeValidationRequest (
    val atm_card_no: String,
    val password: String,
    val confirmPassword: String,
)
