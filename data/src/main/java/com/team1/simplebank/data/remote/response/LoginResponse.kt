package com.team1.simplebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DataResponse? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class DataResponse(

	@field:SerializedName("expiresIn")
	val expiresIn: Int,

	@field:SerializedName("scope")
	val scope: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("tokenType")
	val tokenType: String,

	@field:SerializedName("jti")
	val jti: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)


