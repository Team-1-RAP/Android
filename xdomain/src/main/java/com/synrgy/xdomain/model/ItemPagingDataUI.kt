package com.synrgy.xdomain.model

data class ItemPagingDataUI(
    val amount: Int,
    val date: String,
    val mutationType: String,
    val recipientName: String,
    val recipientTargetAccount: String,
    val transactionStatus: String,
    val transactionType: String,
    val type: String
)
