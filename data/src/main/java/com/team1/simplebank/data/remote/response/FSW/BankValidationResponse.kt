package com.team1.simplebank.data.remote.response.FSW


import com.google.gson.annotations.SerializedName

data class BankValidationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: BankValidationData? = null,
    @SerializedName("message")
    val message: String
)

data class BankValidationData(
    @SerializedName("account_no")
    val accountNo: String,
    @SerializedName("bank_destination")
    val bankDestination: BankDestination,
    @SerializedName("bank_id")
    val bankId: String,
    @SerializedName("recipient_account")
    val recipientAccount: RecipientAccount,
    @SerializedName("user_id")
    val userId: String
)

data class BankDestination(
    @SerializedName("adminFee")
    val adminFee: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class RecipientAccount(
    @SerializedName("account_no")
    val accountNo: String,
    @SerializedName("atm_card_no")
    val atmCardNo: String,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("bank_id")
    val bankId: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String
)