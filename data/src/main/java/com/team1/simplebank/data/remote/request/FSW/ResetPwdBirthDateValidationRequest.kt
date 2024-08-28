package com.team1.simplebank.data.remote.request.FSW

data class ResetPwdBirthDateValidationRequest (
    val atm_card_no : String,
    val born_date : String,
)
