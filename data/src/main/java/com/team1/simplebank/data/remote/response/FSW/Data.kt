package com.team1.simplebank.data.remote.response.FSW


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)