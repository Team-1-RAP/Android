package com.team1.simplebank.data.remote.request.FSW

data class ResetPwdCardValidationRequest (
    val atm_card_no : String,
    val expMonth: Int,
    val expYear: Int,
)
