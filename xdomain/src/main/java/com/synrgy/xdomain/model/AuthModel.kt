package com.synrgy.xdomain.model

data class AuthModel(
    var accessToken : String ?= "",
    var refreshToken : String ?= "",
    var fullName: String ?= "",
    var username: String ?= "",
    var id: Int ?= 0,
)
