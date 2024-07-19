package com.synrgy.xdomain.useCase.user

import com.synrgy.xdomain.repositoryInterface.IUserRepository
import javax.inject.Inject

class GetUserAccountUseCase @Inject constructor(
    private val iUserRepository: IUserRepository
) {

    suspend operator fun invoke(){
       iUserRepository.getUserAccount()
    }

}