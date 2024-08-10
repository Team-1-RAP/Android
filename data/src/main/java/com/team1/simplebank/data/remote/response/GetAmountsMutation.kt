package com.team1.simplebank.data.remote.response


import com.google.gson.annotations.SerializedName

data class GetAmountsMutation(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)


data class Data(
    @SerializedName("income")
    val income: Int,
    @SerializedName("spending")
    val spending: Int
)