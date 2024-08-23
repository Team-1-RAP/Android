package com.team1.simplebank.data.remote.response.BEJ


import com.google.gson.annotations.SerializedName

data class ResultTransferResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val dataItem: ResultTransferData?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class ResultTransferData(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("noRef")
    val noRef: String,
    @SerializedName("recipientBankAccountNo")
    val recipientBankAccountNo: String,
    @SerializedName("recipientBankName")
    val recipientBankName: String,
    @SerializedName("recipientFullName")
    val recipientFullName: String,
    @SerializedName("sourceAccountNo")
    val sourceAccountNo: String,
    @SerializedName("sourceBankName")
    val sourceBankName: String,
    @SerializedName("sourceFullName")
    val sourceFullName: String,
    @SerializedName("transactionId")
    val transactionId: String,
    @SerializedName("transactionType")
    val transactionType: String
)