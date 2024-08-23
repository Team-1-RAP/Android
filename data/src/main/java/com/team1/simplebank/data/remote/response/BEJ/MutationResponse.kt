package com.team1.simplebank.data.remote.response.BEJ


import com.google.gson.annotations.SerializedName

data class MutationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataPaging,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class DataPaging(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pagingData")
    val pagingData: List<ItemPagingData>,
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalItem")
    val totalItem: Int,
    @SerializedName("totalPage")
    val totalPage: Int
)


data class ItemPagingData(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("mutationType")
    val mutationType: String,
    @SerializedName("recipientName")
    val recipientName: String,
    @SerializedName("recipientTargetAccount")
    val recipientTargetAccount: String,
    @SerializedName("transactionStatus")
    val transactionStatus: String,
    @SerializedName("transactionType")
    val transactionType: String,
    @SerializedName("type")
    val type: String
)

