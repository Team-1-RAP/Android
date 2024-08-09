package com.team1.simplebank.data.mapper

import android.util.Log
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.remote.response.ItemPagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

suspend fun convertToDateUI(date: String):String{
    return withContext(Dispatchers.Default){
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        val dateConvert = outputDateFormat.format(inputDateFormat.parse(date))
        dateConvert
    }
}

suspend fun mapperMutationResponseApiToMutationDataUI(dataPaging: List<ItemPagingData>): List<MutationDataUI> {
    return withContext(Dispatchers.Default) {
        val items = mutableListOf<MutationDataUI>()

        items.clear()
        var currentDate: String? = null
        if (dataPaging.isEmpty()) {
            Log.d("mapperMutationResponseApiToMutationDataUI", "empty")
            emptyList()
        } else {
            for (item in dataPaging) {
                //casting date menjadi format tanggal yang sesuai dengan UI
                val itemDate = item.date
                val itemDateConvert = convertToDateUI(itemDate)
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
