package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.GetAmountsMutationUI
import com.team1.simplebank.data.remote.response.GetAmountsMutation

fun mapperGetAmountsMutationToGetAmountsUI(data:GetAmountsMutation):GetAmountsMutationUI{
    return GetAmountsMutationUI(
        data.data.income,
        data.data.spending
    )
}