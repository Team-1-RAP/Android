package com.team1.simplebank.data.remote.response.BEJ


import com.google.gson.annotations.SerializedName

data class GetAllBankResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataGetAllBankResponse>?=null,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class DataGetAllBankResponse(
    @SerializedName("adminFee")
    val adminFee: Int,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("deletedDate")
    val deletedDate: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedDate")
    val updatedDate: String
)