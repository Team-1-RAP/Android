package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.remote.response.ItemPagingData

fun mapperMutationResponseApiToMutationDataUI(dataPaging: List<ItemPagingData>):List<MutationDataUI>{
    val items = mutableListOf<MutationDataUI>()

    dataPaging.groupBy { it.date }.forEach{(date, transaction)->
        items.add(MutationDataUI.Header(date = date))
        items.addAll(transaction.map { detailItem->
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