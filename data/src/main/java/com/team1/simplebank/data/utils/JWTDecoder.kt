package com.team1.simplebank.data.utils

import com.auth0.android.jwt.JWT
import com.synrgy.xdomain.model.AuthModel

fun decodeJwt(accessToken: String, refreshToken: String): AuthModel {
    val jwt = JWT(accessToken)
    return AuthModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = jwt.getClaim("full_name").asString(),
        username = jwt.getClaim("user_name").asString(),
        id = jwt.getClaim("id").asInt()
    )
}