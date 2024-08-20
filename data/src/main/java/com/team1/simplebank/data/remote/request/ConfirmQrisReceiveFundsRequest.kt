package com.team1.simplebank.data.remote.request

data class ConfirmQrisReceiveFundsRequest (
    val qrCode: String,
    val accountNo: String,
)

fun mapConfirmQrisReceiveFundsToRequest(
    qrCode: String,
    accountNo: String,
): ConfirmQrisReceiveFundsRequest {
    return ConfirmQrisReceiveFundsRequest(
        qrCode = qrCode,
        accountNo = accountNo,
    )
}
