package com.team1.simplebank.common.constants

sealed class ScreenRoute (
    var route: String,
){
    data object QrisScan: ScreenRoute("qris_scan")
    data object ScanQrisConfrimPaymentTransaction: ScreenRoute("qris_scan/{qrValue}"){
        fun createRoute(qrValue: String) = "qris_scan/${qrValue}"
    }
    data object ScanQrisConfrimReceivePaymentTransaction: ScreenRoute("qris_scan_receive/{qrValue}"){
        fun createRoute(qrValue: String) = "qris_scan_receive/${qrValue}"
    }
    data object QrisTransactionSuccess: ScreenRoute("qris_transaction_success")

}