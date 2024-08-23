package com.team1.simplebank.data.remote.request.BEJ


data class TransferRequest(
    val accountNo: String,
    val recipientAccountNo: String,
    val recipientBankName: String,
    val amount: Int,
    val pin: String,
    val description: String
)