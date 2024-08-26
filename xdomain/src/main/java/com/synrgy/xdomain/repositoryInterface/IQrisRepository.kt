package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.GenerateQrCodeUiModel
import com.synrgy.xdomain.model.QrisStatusUiModel
import com.synrgy.xdomain.model.ScanQrisUiModel
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow

interface IQrisRepository {

    suspend fun scanQris(qrCode: String) : Flow<ResourceState<ScanQrisUiModel>>
    suspend fun getQrCodeStatus(qrCode: String) : Flow<ResourceState<QrisStatusUiModel>>
    suspend fun confirmQrisMerhant(
        qrCode: String,
        accountNo: String,
        amount: Double,
        pin: String,
    ) : Flow<ResourceState<QrisStatusUiModel>>
    suspend fun confirmQrisReceivesFunds(
        qrCode: String,
        accountNo: String,
    ) : Flow<ResourceState<QrisStatusUiModel>>
    suspend fun generateQrisCode(
        accountNo: String,
        amount: Double,
        pin : String,
    ) : Flow<ResourceState<GenerateQrCodeUiModel>>
}