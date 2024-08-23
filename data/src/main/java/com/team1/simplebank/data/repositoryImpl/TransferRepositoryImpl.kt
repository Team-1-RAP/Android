package com.team1.simplebank.data.repositoryImpl

import com.synrgy.xdomain.model.DataBankModel
import com.synrgy.xdomain.model.ResultTransferModel
import com.synrgy.xdomain.model.SourceAccountModel
import com.synrgy.xdomain.model.ValidationTransferModel
import com.synrgy.xdomain.repositoryInterface.TransferRepository
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.data.mapper.mapGetAllBankResponseToDataBankModel
import com.team1.simplebank.data.mapper.mapperBankValidationResponseToValidationTransferModel
import com.team1.simplebank.data.mapper.mapperGetSourceAccountResponseToSourceAccountModel
import com.team1.simplebank.data.mapper.mapperGetSourceAccountToDataSpinnerSourceAccount
import com.team1.simplebank.data.mapper.mapperTransferResultResponseToTransferResultModel
import com.team1.simplebank.data.remote.api.BEJ.ApiService
import com.team1.simplebank.data.remote.api.FSW.ApiServiceFromFSW
import com.team1.simplebank.data.remote.request.BEJ.TransferRequest
import com.team1.simplebank.data.remote.request.FSW.BankValidationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class TransferRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val apiServiceFromFSW: ApiServiceFromFSW,
) :
    TransferRepository {
    override suspend fun getAllDataBank(): Flow<ResourceState<List<DataBankModel>>> {
        return flow {
            emit(ResourceState.Loading)
            try {
                val response = apiService.getAllBank().data
                if (response != null) {
                    val dataBankModel = mapGetAllBankResponseToDataBankModel(response)
                    emit(ResourceState.Success(dataBankModel))
                } else {
                    emit(ResourceState.Error("Tidak ada data bank yang tersedia"))
                }
            } catch (e: HttpException) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: Exception) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
        }

    }

    override suspend fun getAllSourceAccount(): Flow<ResourceState<List<SourceAccountModel>>> {
        return flow {
            emit(ResourceState.Loading)
            try {
                val response = apiService.getAccounts()
                if (response.data != null) {
                    val data = mapperGetSourceAccountResponseToSourceAccountModel(response.data)
                    emit(ResourceState.Success(data))
                } else {
                    emit(ResourceState.Error("Tidak ada data yang tersedia"))
                }
            } catch (e: HttpException) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: Exception) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
        }
    }

    override suspend fun getDataForSpinnerSourceAccount(): Flow<ResourceState<List<String>>> {
        return flow {
            emit(ResourceState.Loading)
            try {
                val response = apiService.getAccounts()
                if (response.data != null) {
                    val data = mapperGetSourceAccountToDataSpinnerSourceAccount(response.data)
                    emit(ResourceState.Success(data))
                } else {
                    emit(ResourceState.Error("Data Kosong"))
                }
            } catch (e: HttpException) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: Exception) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
        }
    }


    override suspend fun validationAccountTransfer(
        idBank: Int,
        noAccount: String
    ): Flow<ResourceState<ValidationTransferModel>> {
        return flow {
            emit(ResourceState.Loading)
            try {
                val validationTransferModel =
                    BankValidationRequest(bank_id = 1, recipient_no_account = noAccount)
                val response = apiServiceFromFSW.bankValidation(validationTransferModel)
                if (response.data != null) {
                    val bankValidationTransferModel =
                        mapperBankValidationResponseToValidationTransferModel(response.data)
                    emit(ResourceState.Success(bankValidationTransferModel))
                } else {
                    emit(ResourceState.Error("Tidak ada data yang tersedia"))
                }
            } catch (e: HttpException) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: Exception) {
                emit(ResourceState.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
        }
    }

    override suspend fun transfer(
        accountNo: String,
        recipientAccountNo: String,
        recipientBankName: String,
        amount: Int,
        pin: String,
        description: String
    ): Flow<ResourceState<ResultTransferModel>> {
        return flow {
            emit(ResourceState.Loading)
            try {
                val transferRequest = TransferRequest(
                    accountNo,
                    recipientAccountNo,
                    recipientBankName,
                    amount,
                    pin,
                    description
                )
                val response = apiService.transfer(transferRequest).dataItem
                if (response != null) {
                    val result = mapperTransferResultResponseToTransferResultModel(response)
                    emit(ResourceState.Success(result))
                } else {
                    emit(ResourceState.Error("Data Transfer Kosong"))
                }

            } catch (e: HttpException) {
                emit(ResourceState.Error(e.localizedMessage ?: "Un Expected Error Value"))
            } catch (e: Exception) {
                emit(ResourceState.Error(e.localizedMessage ?: "Un Expected Error Value"))
            }
        }
    }


}