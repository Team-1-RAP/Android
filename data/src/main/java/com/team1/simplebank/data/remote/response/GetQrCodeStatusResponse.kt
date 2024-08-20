package com.team1.simplebank.data.remote.response

data class GetQrisStatusResponse(
    val code: Int,
    val data: StatusData?,
    val message: String,
    val status: Boolean
)

data class StatusData(
    val isPaid: Boolean,
    val name: String,
    val amount: Double,
)