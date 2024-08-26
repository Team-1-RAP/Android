package com.synrgy.xdomain.useCase.qris

import com.synrgy.xdomain.model.GenerateQrCodeUiModel
import com.synrgy.xdomain.model.QrisStatusUiModel
import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowQrisTransactionUseCase @Inject constructor(
    private val qrisRepository: IQrisRepository

) {
    suspend fun generateQrisCode(
        accountNo: String,
        amount: Double,
        pin : String,
    ) : Flow<ResourceState<GenerateQrCodeUiModel>> {
        return qrisRepository.generateQrisCode(accountNo, amount, pin)
    }

    suspend fun getQrStatus(
        qrCode: String,
    ) : Flow<ResourceState<QrisStatusUiModel>> {
        return qrisRepository.getQrCodeStatus(qrCode)
    }

}