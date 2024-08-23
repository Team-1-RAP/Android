package com.synrgy.xdomain.model

data class ConfirmationTransferModel(
    val fullUserNameSender:String,
    val accountTypeSender:String,
    val accountNumberSender:String,
    val username: String,
    val fullName: String,
    val accountNumber: String,
    val bankId: String,
    val bankDestination: String,
    val adminFee: Int,
    val totalTransfer:Int,
    val totalTransferWithAdmin:Int,
    val description:String?=null,
)
