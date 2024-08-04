package com.synrgy.xdomain.model

sealed class FilterInput {
    data class NoAccount(val noAccount: String) : FilterInput()
    data class Month(val month: Int) : FilterInput()
    data class Type(val type: String?) : FilterInput()
}

data class FilterStates(
    val noAccount: String,
    val month: Int,
    val type: String?
)
