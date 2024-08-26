package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.QrisStatusUiModel
import com.team1.simplebank.data.remote.response.QrisTransactionData

fun mapConfirmQrisTransactionResponseToConfirmQrisTransactionUiModel(response : QrisTransactionData) : QrisStatusUiModel {
    return QrisStatusUiModel(
        name = response.recipientFullName,
        amount = response.amount,
    )
}