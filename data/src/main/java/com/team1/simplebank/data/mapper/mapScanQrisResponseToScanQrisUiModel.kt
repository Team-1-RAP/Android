package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.ScanQrisUiModel
import com.team1.simplebank.data.remote.response.ScanQrisData

fun mapScanQrisResponseToScanQrisUiModel(response: ScanQrisData): ScanQrisUiModel {
    return response.let {
        ScanQrisUiModel(
            senderName = it.senderName,
            amount = it.amount,
            qrCode = it.qrCode
        )
    }
}