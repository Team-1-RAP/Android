package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.AccountModel
import com.team1.simplebank.common.utils.Converter.toFormatDate
import com.team1.simplebank.data.remote.response.AccountResponse

fun mapUserAccountResponseToUserAccountModel(userAccountResponse: AccountResponse) : List<AccountModel>{
    val userAccountModel = mutableListOf<AccountModel>()
    userAccountResponse.data?.forEach {
        if (it != null) {
            userAccountModel.add(
                AccountModel(
                    balance = it.balance,
                    accountType = it.accountType,
                    fullName = it.fullName,
                    noAccount = it.noAccount,
                    cardNumber = it.cardNumber,
                    expDate = it.expDate.toFormatDate(),
                )
            )
        } else {
            return emptyList()
        }
    }
    return userAccountModel
}