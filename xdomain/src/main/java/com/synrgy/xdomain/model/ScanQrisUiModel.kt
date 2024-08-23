package com.synrgy.xdomain.model

data class ScanQrisUiModel(
    val senderName: String,
    val amount: Double? = null,
    val qrCode: String
)
