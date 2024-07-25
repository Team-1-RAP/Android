package com.synrgy.xdomain.useCase.auth

import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.repositoryInterface.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val IAuthRepository: IAuthRepository
) {
    fun execute(): Flow<AuthModel> {
        return IAuthRepository.getSession()
    }
}