package com.team1.simplebank.data.remote.response.BEJ

import com.google.gson.annotations.SerializedName

data class AccountResponse(

    @field:SerializedName("code")
	val code: Int,

    @field:SerializedName("data")
	val data: List<DataItem?>?,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("status")
	val status: Boolean
)

data class DataItem(

	@field:SerializedName("balance")
	val balance: Int,

	@field:SerializedName("accountType")
	val accountType: String,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("noAccount")
	val noAccount: String,

	@field:SerializedName("cardNumber")
	val cardNumber: String,

	@field:SerializedName("expDate")
	val expDate: String,

	@field:SerializedName("pin")
	val pin: String
)


