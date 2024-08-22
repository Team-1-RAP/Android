package com.synrgy.xdomain.useCase.qris

import com.synrgy.xdomain.model.QrisStatusUiModel
import com.synrgy.xdomain.model.ScanQrisUiModel
import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanQrisTransactionUseCase @Inject constructor(
    private val qrisRepository: IQrisRepository
) {

    suspend operator fun invoke(qrCode: String) : Flow<ResourceState<ScanQrisUiModel>> {
        return qrisRepository.scanQris(qrCode)
    }
}