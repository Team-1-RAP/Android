package com.synrgy.xdomain.model

data class ResultTransferModel(
    val sourceFullName: String,
    val sourceAccount: String,
    val sourceBankName: String,
    val amount: Int,
    val transactionId: String,
    val transactionType: String,
    val recipientBankName: String,
    val recipientBankNoAccount: String,
    val recipientFullName: String,
    val noRef: String,
    val date: String
)
