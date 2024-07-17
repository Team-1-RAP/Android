package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.repositoryInterface.AuthRepository

class ClearSessionUseCase (
    private val authRepository: AuthRepository
) {
    suspend fun execute() {
        authRepository.clearSession()
    }
}