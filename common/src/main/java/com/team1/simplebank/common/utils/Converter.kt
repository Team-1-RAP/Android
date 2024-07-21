package com.team1.simplebank.common.utils

import java.text.NumberFormat
import java.util.Locale

object Converter {
    fun Int.toRupiah():String{
        val localID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        val result = formatRupiah.format(this)
        return result
    }
}