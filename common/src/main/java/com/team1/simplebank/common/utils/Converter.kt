package com.team1.simplebank.common.utils

import android.annotation.SuppressLint
import android.os.Build
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object Converter {
    fun Int.toRupiah():String{
        val localID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        val result = formatRupiah.format(this)
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun String.toFormatDate():String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            val localDateTime = LocalDateTime.parse(this,dateFormatter)
            val formater = DateTimeFormatter.ofPattern("MM/yy")
             val resultDateFormat = formater.format(localDateTime)
            return resultDateFormat
        }else{
                val inputDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                val outputDateTime = SimpleDateFormat("MM/yy")
                val resultDateFormat = inputDateTime.parse(this).let { date->
                    outputDateTime.format(date!!)
                }
                return resultDateFormat

        }
    }
}