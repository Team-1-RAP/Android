package com.team1.simplebank.data.remote.response

import java.time.LocalDateTime

data class ConfirmQrisTransactionResponse(
    val code: Int,
    val data: QrisTransactionData?,
    val message: String,
    val status: Boolean
)

data class QrisTransactionData(
    val sourceFullName: String,
    val sourceAccountNo: String,
    val sourceBankName: String,
    val amount: Double,
    val transactionId: String,
    val transactionType: String,
    val recipientBankName: String,
    val recipientBankAccountNo: String,
    val recipientFullName: String,
    val noRef: String,
    val date: String,
)
