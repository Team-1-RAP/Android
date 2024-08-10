package com.team1.simplebank.common.constants

sealed class ScreenRoute (
    var route: String,
){
    data object QrisScan: ScreenRoute("qris_scan")

    data object QrisTransactionResult: ScreenRoute("qris_transacrion_result")

}