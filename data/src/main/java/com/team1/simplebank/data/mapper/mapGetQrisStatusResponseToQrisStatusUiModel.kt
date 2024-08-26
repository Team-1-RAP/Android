package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.QrisStatusUiModel
import com.team1.simplebank.data.remote.response.StatusData

fun mapGetQrisStatusResponseToQrisStatusUiModel(response: StatusData): QrisStatusUiModel {
    return QrisStatusUiModel(
        name = response.name,
        amount = response.amount,
    )
}