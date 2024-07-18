package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.repositoryInterface.AuthRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(username: String, password: String): Flow<ResourceState<AuthModel>>{
        return authRepository.loginUser(username, password)
    }
}