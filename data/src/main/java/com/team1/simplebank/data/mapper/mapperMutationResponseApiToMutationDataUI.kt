package com.team1.simplebank.data.mapper

import android.util.Log
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.remote.response.ItemPagingData

fun mapperMutationResponseApiToMutationDataUI(dataPaging: List<ItemPagingData>): List<MutationDataUI> {
    val items = mutableListOf<MutationDataUI>()

    if (dataPaging.isEmpty()) {
        Log.d("mapperMutationResponseApiToMutationDataUI", "empty")
        return emptyList()
    } else {
        dataPaging.groupBy { it.date }.forEach { (date, transaction) ->
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
        return items
    }
}