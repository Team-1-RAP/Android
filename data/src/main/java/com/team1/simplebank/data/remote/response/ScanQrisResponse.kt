package com.team1.simplebank.data.remote.response

data class ScanQrisResponse(
	val code: Int,
	val data: ScanQrisData?,
	val message: String,
	val status: Boolean
)

data class ScanQrisData(
	val senderName: String,
	val amount: Double?,
	val qrCode: String
)

