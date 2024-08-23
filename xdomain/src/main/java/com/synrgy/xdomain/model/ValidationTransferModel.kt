package com.synrgy.xdomain.model

data class ValidationTransferModel(
    val username: String,
    val fullName: String,
    val accountNumber: String,
    val bankId: String,
    val bankDestination: String,
    val adminFee: Int,
)
