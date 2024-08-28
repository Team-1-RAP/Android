package com.synrgy.xdomain.repositoryInterface

import com.synrgy.xdomain.model.DataBankModel
import com.synrgy.xdomain.model.DataUserDestinationLocalModel
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


    suspend fun insertNoAccount(userName:String,fullName:String, bankName:String, bankId:Int, noAccount: String,adminFee:Int)
    suspend fun getAllDataNoAccountLocal():Flow<ResourceState<List<DataUserDestinationLocalModel>>>
    suspend fun isItemNoAccountExist(noAccount: String):Boolean
    suspend fun deleteItemNoAccount(noAccount: String)
}