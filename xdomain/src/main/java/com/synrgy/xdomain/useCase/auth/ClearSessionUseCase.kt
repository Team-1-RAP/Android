package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.repositoryInterface.IAuthRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor (
    private val IAuthRepository: IAuthRepository
) {
    suspend operator fun invoke() {
        IAuthRepository.clearSession()

    }
}