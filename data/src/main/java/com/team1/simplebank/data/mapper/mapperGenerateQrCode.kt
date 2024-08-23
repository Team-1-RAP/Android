package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.GenerateQrCodeUiModel
import com.team1.simplebank.data.remote.request.GenerateQrisCodeRequest
import com.team1.simplebank.data.remote.response.QrCodeData

fun mapGenerateQrisCodeModelToRequest(
    accountNo: String,
    amount: Double,
    pin: String
): GenerateQrisCodeRequest {
    return GenerateQrisCodeRequest(
        accountNo = accountNo,
        amount = amount,
        pin = pin
    )
}

fun mapGenerateQrisCodeResponseToGenerateQrisCodeUiModel(
    response: QrCodeData
): GenerateQrCodeUiModel {
    return GenerateQrCodeUiModel(
        qrCode = response.qrCode,
        timeOut = response.dueDate
    )
}