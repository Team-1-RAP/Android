package com.team1.simplebank.data.remote.request.FSW

data class ResetPwdPinValidationRequest (
    val atm_card_no: String,
    val pin: String,
)