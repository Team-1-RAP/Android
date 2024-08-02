package com.synrgy.xdomain.model

sealed class MutationDataUI {
    data class Header(val date: String) : MutationDataUI()
    data class Item(
        val transactionType: String,
        val mutationType: String,
        val recipientName: String,
        val type: String,
        val amount: Int,
        val recipientTargetAccount: String,
        val transactionStatus: String
    ):MutationDataUI()
}