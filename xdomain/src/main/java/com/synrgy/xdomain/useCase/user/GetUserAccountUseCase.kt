package com.synrgy.xdomain.useCase.user

import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.repositoryInterface.IUserRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAccountUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {

    suspend operator fun invoke(): Flow<ResourceState<List<AccountModel>>>{
       return iUserRepository.getUserAccount()
    }

    suspend fun getUserAccounts(): Flow<ResourceState<List<AccountModel>>> {
        return iUserRepository.getUserAccount()
    }

    suspend fun saveNoAccount(noAccountInput:String){
        iUserRepository.saveNoAccount(noAccountInput)
    }

    fun getNoAccount():Flow<String?>{
        return iUserRepository.getNoAccount()
    }

}