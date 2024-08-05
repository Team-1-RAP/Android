package com.team1.simplebank.data.mapper

import android.util.Log
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.remote.response.ItemPagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

suspend fun mapperMutationResponseApiToMutationDataUI(dataPaging: List<ItemPagingData>): List<MutationDataUI> {
    return withContext(Dispatchers.Default) {


        val items = mutableListOf<MutationDataUI>()
        val localeID = Locale("in", "ID")
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", localeID)

        items.clear()
        var currentDate: String? = null
        if (dataPaging.isEmpty()) {
            Log.d("mapperMutationResponseApiToMutationDataUI", "empty")
            emptyList()
        } else {
            /*dataPaging.groupBy { it.date }.forEach { (date, transaction) ->
                Log.d("mapperMutationResponseApiToMutationDataUI", "di casting")

                items.add(MutationDataUI.Header(date = date))
                items.addAll(transaction.map { detailItem ->
                    MutationDataUI.Item(
                        transactionType = detailItem.transactionType,
                        mutationType = detailItem.mutationType,
                        recipientName = detailItem.recipientName,
                        type = detailItem.type,
                        amount = detailItem.amount,
                        recipientTargetAccount = detailItem.recipientTargetAccount,
                        transactionStatus = detailItem.transactionStatus

                    )
                })
            }
            return items*/

            for (item in dataPaging) {
                //casting date menjadi format tanggal yang sesuai dengan UI
                val itemDate = item.date
                val itemDateConvert =
                    outputDateFormat.format(inputDateFormat.parse(itemDate) as Date)
                //val itemDateConvert = itemDate.toTransactionDate()
                Log.d("before", "original date: $itemDate, converted date: $itemDateConvert")
                if (itemDateConvert != currentDate) {
                    currentDate = itemDateConvert
                    items.add(MutationDataUI.Header(date = currentDate))
                    Log.d("after", "added header for date: $currentDate")
                }
                items.add(
                    MutationDataUI.Item(
                        item.transactionType,
                        item.mutationType,
                        item.recipientName,
                        item.type,
                        item.amount,
                        item.recipientTargetAccount,
                        item.transactionStatus
                    )
                )
            }
            items
        }
    }
}
