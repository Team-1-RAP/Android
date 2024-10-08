package com.team1.simplebank.common.utils

import android.annotation.SuppressLint
import android.os.Build
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Converter {
    fun Int.toRupiah(): String {
        val localID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        val result = formatRupiah.format(this)
        return result.replace("Rp", "Rp.")

    }

    fun Double.toRupiah(): String {
        val decimalFormatSymbols = DecimalFormatSymbols(Locale("in", "ID")).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        val decimalFormat = DecimalFormat("#,##0.00", decimalFormatSymbols)
        return decimalFormat.format(this)
    }

    @SuppressLint("SimpleDateFormat")
    fun String.toFormatDate(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            val localDateTime = LocalDateTime.parse(this, dateFormatter)
            val formater = DateTimeFormatter.ofPattern("MM/yy")
            val resultDateFormat = formater.format(localDateTime)
            return resultDateFormat
        } else {
            val inputDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            val outputDateTime = SimpleDateFormat("MM/yy")
            val resultDateFormat = inputDateTime.parse(this).let { date ->
                outputDateTime.format(date!!)
            }
            return resultDateFormat

        }
    }

    fun String.toMonthNumber(): Int {
        val localeID = Locale("in", "ID")

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Format bulan ke angka menggunakan DateTimeFormatter
                val monthFormatter = DateTimeFormatter.ofPattern("MMMM", localeID)
                val parsedMonth = monthFormatter.parse(this)
                parsedMonth.get(ChronoField.MONTH_OF_YEAR)
            } else {
                // Format bulan ke angka menggunakan SimpleDateFormat
                val monthFormatterID = SimpleDateFormat("MMMM", localeID)
                val parsedMonth = monthFormatterID.parse(this)
                val calendar = Calendar.getInstance().apply {
                    time = parsedMonth as Date
                }
                calendar.get(Calendar.MONTH) + 1
            }
        } catch (e: Exception) {
            // Jika parsing gagal, kembalikan bulan saat ini
            val calendar = Calendar.getInstance()
            calendar.get(Calendar.MONTH) + 1
        }
    }

    fun mapperDateTimeToDateTransferResult(dateFormat: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        val dateConvert = outputDateFormat.format(inputDateFormat.parse(dateFormat) as Date)
        return dateConvert
    }

    fun mapperDateTimeToTimeTransferResult(dateFormat: String): String {
            //casting input date format (string) ke Date
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            inputDateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            //casting+split output date format (string) ke Date (ambil waktunya saja)
            val outputFormat = SimpleDateFormat("HH:mm:ss", Locale("in", "ID"))
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            //format dateFormat(inputan) menjadi DateInputFormat
            val date = inputDateFormat.parse(dateFormat)

            //format date (Date) ke dalam output format
            return outputFormat.format(date ?: Date()) // Handle potential null values
        }
}