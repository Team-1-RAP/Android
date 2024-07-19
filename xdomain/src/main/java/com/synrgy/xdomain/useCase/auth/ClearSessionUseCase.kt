package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.repositoryInterface.AuthRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.clearSession()

    }
}