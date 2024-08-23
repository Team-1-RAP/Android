package com.team1.simplebank.data.repositoryImpl

import com.synrgy.xdomain.model.GetAmountsMutationUI
import com.synrgy.xdomain.repositoryInterface.IUserRepository
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.data.dataStore.AuthDataStore
import com.team1.simplebank.data.mapper.mapUserAccountResponseToUserAccountModel
import com.team1.simplebank.data.mapper.mapperGetAmountsMutationToGetAmountsUI
import com.team1.simplebank.data.remote.api.BEJ.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
) : IUserRepository {

    override suspend fun getUserAccount() = flow {
        emit(ResourceState.Loading)
        try {
            val response = apiService.getAccounts()
            //val responseMutation = apiService.getMutations("3737657598213562",1)
            //Log.d("Response", "getUserAccount: $responseMutation")
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
    override suspend fun saveNoAccount(noAccountInput: String){
        authDataStore.saveNoAccount(noAccountInput)
    }

    override fun getNoAccount(): Flow<String?> {
        return authDataStore.getNoAccount()
    }


    override suspend fun getAmounts(noAccount: String): Flow<ResourceState<GetAmountsMutationUI>> {
        return flow{
            emit(ResourceState.Loading)
            try {
                val responseGetAmounts = apiService.getMutationsAmount(noAccount)
                if (responseGetAmounts!=null){
                    val data = mapperGetAmountsMutationToGetAmountsUI(responseGetAmounts)
                    emit(ResourceState.Success(data))
                }else{
                    emit(ResourceState.Error("Tidak ada data yang tersedia"))
                }
            }catch (exception:Exception){
                emit(ResourceState.Error(exception.localizedMessage?:"An unexpected error occured"))
            }
        }
    }
}