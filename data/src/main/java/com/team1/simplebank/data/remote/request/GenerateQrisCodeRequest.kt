package com.team1.simplebank.data.remote.request

data class GenerateQrisCodeRequest (
    val accountNo: String,
    val amount: Double,
    val pin : String,
)