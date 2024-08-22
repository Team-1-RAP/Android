package com.synrgy.xdomain.useCase.qris

import com.synrgy.xdomain.model.QrisStatusUiModel
import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfirmQrisTransactionUseCase @Inject constructor(
    private val qrisRepository: IQrisRepository
) {
    suspend fun confirmQrisMerhant(
        qrCode: String,
        accountNo: String,
        amount: Double,
        pin: String,
    ) : Flow<ResourceState<QrisStatusUiModel>> {
        return qrisRepository.confirmQrisMerhant(qrCode, accountNo, amount, pin)
    }
    suspend fun confirmQrisReceivesFunds(
        qrCode: String,
        accountNo: String,
    ) : Flow<ResourceState<QrisStatusUiModel>> {
        return qrisRepository.confirmQrisReceivesFunds(qrCode, accountNo)
    }
}