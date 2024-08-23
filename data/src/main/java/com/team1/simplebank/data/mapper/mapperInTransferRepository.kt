package com.team1.simplebank.data.mapper

import com.synrgy.xdomain.model.DataBankModel
import com.synrgy.xdomain.model.ResultTransferModel
import com.synrgy.xdomain.model.SourceAccountModel
import com.synrgy.xdomain.model.ValidationTransferModel
import com.team1.simplebank.data.remote.response.BEJ.DataGetAllBankResponse
import com.team1.simplebank.data.remote.response.BEJ.DataItem
import com.team1.simplebank.data.remote.response.BEJ.ResultTransferData
import com.team1.simplebank.data.remote.response.FSW.BankValidationData

//mapper untuk list bank
fun mapGetAllBankResponseToDataBankModel(data: List<DataGetAllBankResponse?>): List<DataBankModel> {
    val dataBankModel = mutableListOf<DataBankModel>()
    data.forEach {
        if (it != null) {
            dataBankModel.add(
                DataBankModel(
                    id = it.id,
                    bankName = it.bankName
                )
            )
        } else {
            return emptyList()
        }
    }

    return dataBankModel
}

//mapper untuk merubah respon sumber akun ke model akun sumber
fun mapperGetSourceAccountResponseToSourceAccountModel(data: List<DataItem?>): List<SourceAccountModel> {
    val sourceAccountModel = mutableListOf<SourceAccountModel>()
    data.forEach {
        if (it != null) {
            sourceAccountModel.add(
                SourceAccountModel(
                    fullName = it.fullName,
                    noAccount = it.noAccount,
                    accountType = it.accountType
                )
            )
        } else {
            return emptyList()
        }
    }
    return sourceAccountModel
}

//merubah response list sumber akun menjadi list data untuk spinner
fun mapperGetSourceAccountToDataSpinnerSourceAccount(dataInput: List<DataItem?>): List<String> {
    val dataSpinnerSourceAccount = mutableListOf<String>()
    dataInput.forEach {
        if (it != null) {
            dataSpinnerSourceAccount.add(it.noAccount)
        } else {
            return emptyList()
        }
    }
    return dataSpinnerSourceAccount
}

// merubah response validasi bank ke model validasi transfer
fun mapperBankValidationResponseToValidationTransferModel(data: BankValidationData): ValidationTransferModel {
    return ValidationTransferModel(
        username = data.recipientAccount.username,
        fullName = data.recipientAccount.fullname,
        accountNumber = data.recipientAccount.accountNo,
        bankId = data.bankId,
        bankDestination = data.bankDestination.name,
        adminFee = data.bankDestination.adminFee
    )
}

//merubah response result transfer ke model result transfer
fun mapperTransferResultResponseToTransferResultModel(data: ResultTransferData): ResultTransferModel {
    return ResultTransferModel(
        sourceFullName = data.sourceFullName,
        sourceAccount = data.sourceAccountNo,
        sourceBankName = data.sourceBankName,
        amount = data.amount,
        transactionId = data.transactionId,
        transactionType = data.transactionType,
        recipientBankName = data.recipientBankName,
        recipientBankNoAccount = data.recipientBankAccountNo,
        recipientFullName = data.recipientFullName,
        noRef = data.noRef,
        date = data.date
    )
}