package com.team1.simplebank.ui.home.transfer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.ConfirmationTransferModel
import com.synrgy.xdomain.model.ResultTransferModel
import com.synrgy.xdomain.model.SourceAccountModel
import com.synrgy.xdomain.model.TypeAccountModel
import com.synrgy.xdomain.model.ValidationTransferModel
import com.synrgy.xdomain.useCase.transfer.TransferUseCase
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferSharedViewModel @Inject constructor
    (private val transferUseCase: TransferUseCase) : ViewModel() {

    private var _dataValidationTransferSuccess: MutableStateFlow<ResourceState<ValidationTransferModel>> =
        MutableStateFlow(ResourceState.Idle)

    val dataValidationTransferSuccess: StateFlow<ResourceState<ValidationTransferModel>> =
        _dataValidationTransferSuccess.asStateFlow()

    private var _dataSourceAccountChoosing: MutableStateFlow<ResourceState<SourceAccountModel>> =
        MutableStateFlow(ResourceState.Idle)

    val dataSourceAccountChoosing: StateFlow<ResourceState<SourceAccountModel>> =
        _dataSourceAccountChoosing.asStateFlow()

    private var _mergeAllDataTransfer: MutableStateFlow<ResourceState<ConfirmationTransferModel>> =
        MutableStateFlow(ResourceState.Idle)

    val mergeAllDataTransfer: StateFlow<ResourceState<ConfirmationTransferModel>> =
        _mergeAllDataTransfer.asStateFlow()

    private var _dataResultTransfer: MutableStateFlow<ResourceState<ResultTransferModel>> =
        MutableStateFlow(ResourceState.Idle)

    val dataResultTransfer: StateFlow<ResourceState<ResultTransferModel>> =
        _dataResultTransfer.asStateFlow()


    //data untuk spinner source account
    val getDataForSourceAccountSpinner: LiveData<ResourceState<List<String>>> = liveData {
        transferUseCase.getDataSpinnerSourceAccount().collect {
            when (it) {
                ResourceState.Loading -> {
                    emit(ResourceState.Loading)
                }

                is ResourceState.Success -> {
                    emit(ResourceState.Success(it.data))
                }

                is ResourceState.Error -> {
                    emit(ResourceState.Error(it.exception))
                }

                ResourceState.Idle -> {}

            }
        }
    }


    val getAllDataSourceAccount: LiveData<ResourceState<List<SourceAccountModel>>> = liveData {
        transferUseCase.getAllSourceAccount().collect {
            when (it) {
                ResourceState.Loading -> {
                    emit(ResourceState.Loading)
                }

                is ResourceState.Success -> {
                    emit(ResourceState.Success(it.data))
                }

                is ResourceState.Error -> {
                    emit(ResourceState.Error(it.exception))
                }

                ResourceState.Idle -> {}

            }

        }
    }


    //penggunaan callback untuk mendapatkan hasil apakah cekValidasi ini berhasil atau tidak
    //cara tersebut digunakan untuk menghindari observable terus menerus
    fun cekValidationTransfer(
        id: Int,
        noAccount: String,
        onLoading: (Boolean) -> Unit,
        onResult: (Boolean) -> Unit

    ) {
        viewModelScope.launch {
            transferUseCase.validationAccountTransfer(id, noAccount).collect {
                when (it) {
                    is ResourceState.Loading -> {
                        onLoading(true)
                        _dataValidationTransferSuccess.value = ResourceState.Loading

                    }

                    is ResourceState.Success -> {
                        onLoading(false)
                        _dataValidationTransferSuccess.value =
                            ResourceState.Success(it.data)
                        onResult(true)
                        Log.d(
                            "validationAccountTransfer",
                            "cekValidationTransfer: ${it.data}"
                        )
                    }

                    is ResourceState.Error -> {
                        onLoading(false)
                        _dataValidationTransferSuccess.value =
                            ResourceState.Error(it.exception)
                        onResult(false)
                    }

                    is ResourceState.Idle -> {}
                }
            }
        }
    }

    fun setDataSourceAccountChose(fullName: String, accountType: String, noAccount: String) {
        _dataSourceAccountChoosing.value = ResourceState.Loading
        try {
            val dataSourceAccountChoosing = SourceAccountModel(
                fullName = fullName,
                accountType = accountType,
                noAccount = noAccount
            )
            _dataSourceAccountChoosing.value = ResourceState.Success(dataSourceAccountChoosing)
        } catch (e: Exception) {
            _dataSourceAccountChoosing.value = ResourceState.Error("Data Nothing")
        }
    }

    fun merAllDataTransfer(totalTransferInput: Int) {
        viewModelScope.launch {
            combine(
                dataValidationTransferSuccess,
                dataSourceAccountChoosing,
            ) { validationSuccess, dataSourceAccountChoosing ->
                Pair(validationSuccess, dataSourceAccountChoosing)
            }.collect { (validationSuccess, dataSourceAccountChoosing) ->
                when {
                    validationSuccess is ResourceState.Loading || dataSourceAccountChoosing is ResourceState.Loading -> {
                        _mergeAllDataTransfer.value = ResourceState.Loading
                    }

                    validationSuccess is ResourceState.Success && dataSourceAccountChoosing is ResourceState.Success -> {
                        val resultTotal = totalTransferInput - validationSuccess.data.adminFee
                        val dataResult = ConfirmationTransferModel(
                            fullUserNameSender = dataSourceAccountChoosing.data.fullName,
                            accountTypeSender = dataSourceAccountChoosing.data.accountType,
                            accountNumberSender = dataSourceAccountChoosing.data.noAccount,
                            username = validationSuccess.data.username,
                            fullName = validationSuccess.data.fullName,
                            accountNumber = validationSuccess.data.accountNumber,
                            bankId = validationSuccess.data.bankId,
                            bankDestination = validationSuccess.data.bankDestination,
                            adminFee = validationSuccess.data.adminFee,
                            totalTransfer = totalTransferInput,
                            totalTransferWithAdmin = resultTotal,
                            description = "Aku mau ngirim"
                        )
                        _mergeAllDataTransfer.value = ResourceState.Success(dataResult)
                    }

                    validationSuccess is ResourceState.Error -> {
                        _mergeAllDataTransfer.value =
                            ResourceState.Error(validationSuccess.exception)
                    }

                    dataSourceAccountChoosing is ResourceState.Error -> {
                        _mergeAllDataTransfer.value =
                            ResourceState.Error(dataSourceAccountChoosing.exception)
                    }

                    else -> {
                        _mergeAllDataTransfer.value = ResourceState.Idle
                    }
                }
            }
        }
    }

    //fungsi untuk mengirim uang
    fun transfer(
        accountNo: String,
        recipientAccountNo: String,
        recipientBankName: String,
        amount: Int,
        pin: String,
        description: String,
        onLoading: (Boolean) -> Unit,
        onResult: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            transferUseCase.transfer(
                accountNo,
                recipientAccountNo,
                recipientBankName,
                amount,
                pin,
                description
            ).collect {
                when (it) {
                    ResourceState.Loading -> {
                        onLoading(true)
                        _dataResultTransfer.value = ResourceState.Loading
                    }

                    is ResourceState.Success -> {
                        onLoading(false)
                        _dataResultTransfer.value = ResourceState.Success(it.data)
                        onResult(true)
                    }

                    is ResourceState.Error -> {
                        onLoading(false)
                        _dataResultTransfer.value = ResourceState.Error(it.exception)
                        onResult(false)
                    }

                    ResourceState.Idle -> {
                        //do nothing
                    }
                }
            }
        }
    }
}






