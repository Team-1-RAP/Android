package com.team1.simplebank.data.remote.request

data class ConfirmQrisMerchantRequest(
    val qrCode: String,
    val accountNo: String,
    val amount: Double,
    val pin: String,
)
