package com.team1.simplebank.data.remote.request

data class ConfirmQrisMerchantRequest(
    val qrCode: String,
    val accountNo: String,
    val amount: Double,
    val pin: String,
)

fun mapConfirmQrisMerchantModelToRequest(
    qrCode: String,
    accountNo: String,
    amount: Double,
    pin: String
): ConfirmQrisMerchantRequest {
    return ConfirmQrisMerchantRequest(
        qrCode = qrCode,
        accountNo = accountNo,
        amount = amount,
        pin = pin
    )
}
