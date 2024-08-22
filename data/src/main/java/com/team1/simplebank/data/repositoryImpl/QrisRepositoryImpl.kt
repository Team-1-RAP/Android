package com.team1.simplebank.data.repositoryImpl

import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.data.mapper.mapConfirmQrisTransactionResponseToConfirmQrisTransactionUiModel
import com.team1.simplebank.data.mapper.mapGenerateQrisCodeModelToRequest
import com.team1.simplebank.data.mapper.mapGenerateQrisCodeResponseToGenerateQrisCodeUiModel
import com.team1.simplebank.data.mapper.mapGetQrisStatusResponseToQrisStatusUiModel
import com.team1.simplebank.data.mapper.mapScanQrisResponseToScanQrisUiModel
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.request.mapConfirmQrisMerchantModelToRequest
import com.team1.simplebank.data.remote.request.mapConfirmQrisReceiveFundsToRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QrisRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : IQrisRepository {


    override suspend fun scanQris(qrCode: String) = flow {
        emit(ResourceState.Loading)
        try {
            val response = apiService.scanQris(qrCode)
            if (response.code == 200) {
                val responseData = response.data
                if (responseData != null) {
                    emit(ResourceState.Success(mapScanQrisResponseToScanQrisUiModel(responseData)))
                } else {
                    emit(ResourceState.Error("Tidak ada data (null)"))
                }
            } else {
                emit(ResourceState.Error(response.message))
            }
        } catch (e: Exception) {
            emit(ResourceState.Error(e.localizedMessage ?: "Terjadi kesalahan, harap coba lagi"))
        }
    }

    override suspend fun getQrCodeStatus(qrCode: String) = flow {
        emit(ResourceState.Loading)
        try {
            val response = apiService.getQrCodeStatus(qrCode)
            if (response.code == 200) {
                val responseData = response.data
                if (responseData != null) {
                    emit(
                        ResourceState.Success(
                            mapGetQrisStatusResponseToQrisStatusUiModel(
                                responseData
                            )
                        )
                    )
                } else {
                    emit(ResourceState.Error("Tidak ada data (null)"))
                }
            } else {
                emit(ResourceState.Error(response.message))
            }
        } catch (e: Exception) {
            emit(ResourceState.Error("Terjadi kesalahan, harap coba lagi"))
        }
    }

    override suspend fun confirmQrisMerhant(
        qrCode: String,
        accountNo: String,
        amount: Double,
        pin: String,
    ) = flow {
        emit(ResourceState.Loading)
        try {
            val requestBody = mapConfirmQrisMerchantModelToRequest(qrCode, accountNo, amount, pin)
            val response = apiService.confirmQrisMerhant(requestBody)
            if (response.code == 200) {
                val responseData = response.data
                if (responseData != null) {
                    emit(
                        ResourceState.Success(
                            mapConfirmQrisTransactionResponseToConfirmQrisTransactionUiModel(
                                responseData
                            )
                        )
                    )
                } else {
                    emit(ResourceState.Error("Tidak ada data (null)"))
                }
            } else {
                emit(ResourceState.Error(response.message))
            }
        } catch (e: Exception) {
            emit(ResourceState.Error("Terjadi kesalahan, harap coba lagi"))
        }
    }

    override suspend fun confirmQrisReceivesFunds(qrCode: String, accountNo: String) = flow {
        emit(ResourceState.Loading)
        try {
            val requestBody = mapConfirmQrisReceiveFundsToRequest(qrCode, accountNo)
            val response = apiService.confirmQrisReceivesFunds(requestBody)
            if (response.code == 200) {
                val responseData = response.data
                if (responseData != null) {
                    emit(
                        ResourceState.Success(
                            mapConfirmQrisTransactionResponseToConfirmQrisTransactionUiModel(
                                responseData
                            )
                        )
                    )
                } else {
                    emit(ResourceState.Error("Tidak ada data (null)"))
                }
            } else {
                emit(ResourceState.Error(response.message))
            }
        } catch (e: Exception) {
            emit(ResourceState.Error("Terjadi kesalahan, harap coba lagi"))
        }
    }

    override suspend fun generateQrisCode(
        accountNo: String,
        amount: Double,
        pin: String,
    ) = flow {
        emit(ResourceState.Loading)
        try {
            val requestBody = mapGenerateQrisCodeModelToRequest(accountNo, amount, pin)
            val response = apiService.generateQrisCode(requestBody)
            if (response.code == 200) {
                val responseData = response.data
                if (responseData != null) {
                    emit(
                        ResourceState.Success(
                            mapGenerateQrisCodeResponseToGenerateQrisCodeUiModel(
                                responseData
                            )
                        )
                    )
                } else {
                    emit(ResourceState.Error("Tidak ada data"))
                }
            } else {
                emit(ResourceState.Error(response.message))
            }
        } catch (e: Exception) {
            emit(ResourceState.Error( "Terjadi kesalahan, harap coba lagi"))
        }
    }

}