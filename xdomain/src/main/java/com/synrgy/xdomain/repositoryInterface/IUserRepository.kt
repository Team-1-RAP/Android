package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.AccountModel
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow


interface IUserRepository {
    suspend fun getUserAccount() : Flow<ResourceState<List<AccountModel>>>
    suspend fun saveNoAccount(noAccountInput:String)
    fun getNoAccount():Flow<String?>

}