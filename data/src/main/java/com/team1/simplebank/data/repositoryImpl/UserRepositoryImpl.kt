package com.team1.simplebank.data.repositoryImpl

import com.synrgy.xdomain.repositoryInterface.IUserRepository
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.data.mapper.mapUserAccountResponseToUserAccountModel
import com.team1.simplebank.data.remote.api.ApiService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : IUserRepository {

    override suspend fun getUserAccount() = flow {
        emit(ResourceState.Loading)
        try {
            val response = apiService.getAccounts()
            if (response.data != null) {
                val data = mapUserAccountResponseToUserAccountModel(response)
                emit(ResourceState.Success(data))
            } else {
                emit(ResourceState.Error("Belum memiliki akun"))
            }
        } catch (e: HttpException) {
            emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}