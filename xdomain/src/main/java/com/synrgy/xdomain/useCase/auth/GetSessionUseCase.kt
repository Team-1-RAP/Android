package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.repositoryInterface.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetSessionUseCase (
    private val authRepository: AuthRepository
) {
    fun execute(): Flow<AuthModel> {
        return authRepository.getSession()
    }
}