package com.synrgy.xdomain.model

data class DropDownItem(
    val noRekening: String,
)

val dropDownItem = listOf(
    DropDownItem("SILVER - 0022200"),
    DropDownItem("GOLD - 0202"),
    DropDownItem("PLATINUM - 0101"),
    DropDownItem("BLACK - 0033300"),
)