package com.team1.simplebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenerateQrisCodeResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: QrCodeData?,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class QrCodeData(

	@field:SerializedName("qrCode")
	val qrCode: String,

	@field:SerializedName("dueDate")
	val dueDate: String
)
