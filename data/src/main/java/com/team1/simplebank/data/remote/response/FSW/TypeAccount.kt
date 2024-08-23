package com.team1.simplebank.data.remote.response.FSW


import com.google.gson.annotations.SerializedName

data class TypeAccount(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)