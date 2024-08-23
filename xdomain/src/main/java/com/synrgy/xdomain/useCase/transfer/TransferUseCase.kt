package com.synrgy.xdomain.useCase.transfer

import com.synrgy.xdomain.model.DataBankModel
import com.synrgy.xdomain.model.ResultTransferModel
import com.synrgy.xdomain.model.SourceAccountModel
import com.synrgy.xdomain.model.TypeAccountModel
import com.synrgy.xdomain.model.ValidationTransferModel
import com.synrgy.xdomain.repositoryInterface.TransferRepository
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Class yang digunakan untuk memanggil repository yang digunakan di kelas TransferPage
class TransferUseCase @Inject constructor(val transferRepository: TransferRepository) {
    suspend fun getAllDataBank(): Flow<ResourceState<List<DataBankModel>>> {
        return transferRepository.getAllDataBank()
    }

    suspend fun getAllSourceAccount(): Flow<ResourceState<List<SourceAccountModel>>> {
        return transferRepository.getAllSourceAccount()
    }

    suspend fun getDataSpinnerSourceAccount(): Flow<ResourceState<List<String>>> {
        return transferRepository.getDataForSpinnerSourceAccount()
    }

    suspend fun validationAccountTransfer(
        id: Int,
        noAccount: String
    ): Flow<ResourceState<ValidationTransferModel>> {
        return transferRepository.validationAccountTransfer(id, noAccount)
    }

    suspend fun transfer(
        accountNo: String,
        recipientAccountNo: String,
        recipientBankName: String,
        amount: Int,
        pin: String,
        description: String
    ): Flow<ResourceState<ResultTransferModel>> {
        return transferRepository.transfer(
            accountNo,
            recipientAccountNo,
            recipientBankName,
            amount,
            pin,
            description
        )
    }
}