package com.team1.simplebank.data.remote.request

data class ConfirmQrisReceiveFundsRequest (
    val qrCode: String,
    val accountNo: String,
)