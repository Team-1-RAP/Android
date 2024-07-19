package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.AuthModel
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun loginUser(username: String, password: String): Flow<ResourceState<AuthModel>>
    fun getSession(): Flow<AuthModel>
    suspend fun clearSession()
}