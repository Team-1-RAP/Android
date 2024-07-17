package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.AuthModel
import com.team1.simplebank.data.remote.response.DataResponse

fun mapLoginDataResponseToAuthModel(response: DataResponse): AuthModel {
    return AuthModel(
        accessToken = response.accessToken,
        refreshToken = response.refreshToken,
    )
}