package com.team1.simplebank.data.repositoryImpl

import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.repositoryInterface.IAuthRepository
import com.team1.simplebank.data.utils.decodeJwt
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.data.dataStore.AuthDataStore
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.request.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore,
) : IAuthRepository {
    override suspend fun loginUser(username: String, password: String) = flow {
        emit(ResourceState.Loading)
        try {
            val request = LoginRequest(username = username, password = password)
            val response = apiService.loginUser(request)
            if(response.data != null){
                val accessToken = response.data.accessToken
                val refreshToken = response.data.refreshToken
                val authData = decodeJwt(accessToken,refreshToken)
                authDataStore.saveSession(authData)
                emit(ResourceState.Success(authData))
            } else {
                emit(ResourceState.Error("Silakan periksa kembali username dan password Anda"))
            }
        } catch (e: HttpException){
            emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: Exception){
            emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

    override fun getSession(): Flow<AuthModel> {
        return authDataStore.getUserSession()
    }

    override suspend fun clearSession() {
        authDataStore.clearSession()
    }
}