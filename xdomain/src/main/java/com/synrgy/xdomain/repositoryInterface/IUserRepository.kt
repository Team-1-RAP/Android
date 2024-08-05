package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.GetAmountsMutationUI
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface IUserRepository {
    suspend fun getUserAccount() : Flow<ResourceState<List<AccountModel>>>
    suspend fun saveNoAccount(noAccountInput:String)
    fun getNoAccount():Flow<String?>

    suspend fun getAmounts(noAccount:String):Flow<ResourceState<GetAmountsMutationUI>>

}