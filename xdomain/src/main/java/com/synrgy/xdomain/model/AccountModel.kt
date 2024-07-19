package com.synrgy.xdomain.model

data class AccountModel(
    val balance: Int,
    val accountType: String,
    val fullName: String,
    val noAccount: String,
    val cardNumber: String,
    val expDate: String
)
