package com.synrgy.xdomain.useCase.qris

import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import javax.inject.Inject

class ScanQrisTransactionUseCase @Inject constructor(
    private val qrisRepository: IQrisRepository
) {

    suspend operator fun invoke(qrCode: String){
        qrisRepository.scanQris(qrCode)
    }
}