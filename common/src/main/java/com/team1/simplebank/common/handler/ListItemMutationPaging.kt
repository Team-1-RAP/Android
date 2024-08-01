package com.team1.simplebank.common.handler

import com.synrgy.xdomain.model.ItemPagingDataUI

sealed class ListItemMutationPaging {
    data class Header(val title: String) : ListItemMutationPaging()
    data class Item(val data:ItemPagingDataUI):ListItemMutationPaging()
}