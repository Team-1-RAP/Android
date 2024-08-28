package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.DataBankModel
import com.synrgy.xdomain.model.ResultTransferModel
import com.synrgy.xdomain.model.SourceAccountModel
import com.synrgy.xdomain.model.ValidationTransferModel
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    suspend fun getAllDataBank(): Flow<ResourceState<List<DataBankModel>>>
    suspend fun getAllSourceAccount(): Flow<ResourceState<List<SourceAccountModel>>>
    suspend fun getDataForSpinnerSourceAccount(): Flow<ResourceState<List<String>>>
    suspend fun validationAccountTransfer(idBank: Int, noAccount: String): Flow<ResourceState<ValidationTransferModel>>
    suspend fun transfer(accountNo: String, recipientAccountNo: String, recipientBankName: String, amount: Int, pin: String, description: String): Flow<ResourceState<ResultTransferModel>>
}