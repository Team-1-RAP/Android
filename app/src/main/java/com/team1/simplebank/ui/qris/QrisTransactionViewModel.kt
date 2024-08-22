package com.team1.simplebank.ui.qris

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.GenerateQrCodeUiModel
import com.synrgy.xdomain.model.QrisStatusUiModel
import com.synrgy.xdomain.model.ScanQrisUiModel
import com.synrgy.xdomain.useCase.qris.ConfirmQrisTransactionUseCase
import com.synrgy.xdomain.useCase.qris.ScanQrisTransactionUseCase
import com.synrgy.xdomain.useCase.qris.ShowQrisTransactionUseCase
import com.synrgy.xdomain.useCase.user.GetUserAccountUseCase
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrisTransactionViewModel @Inject constructor(
    private val scanQrisTransactionUseCase: ScanQrisTransactionUseCase,
    private val showQrisTransactionUseCase: ShowQrisTransactionUseCase,
    private val confirmQrisTransactionUseCase: ConfirmQrisTransactionUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
) : ViewModel() {


    // scanQris
    private val _scannedData = MutableStateFlow<ResourceState<ScanQrisUiModel>>(ResourceState.Idle)
    val scannedData = _scannedData.asStateFlow()

    private val _userRekeningData =
        MutableStateFlow<ResourceState<List<AccountModel>>>(ResourceState.Idle)
    val userRekeningData = _userRekeningData.asStateFlow()

    private val _merchantQrisPaymentTransactionData =
        MutableStateFlow<ResourceState<QrisStatusUiModel>>(ResourceState.Idle)
    val merchantQrisPaymentTransactionData = _merchantQrisPaymentTransactionData.asStateFlow()

    private val _generatedQrisCodeData =
        MutableStateFlow<ResourceState<GenerateQrCodeUiModel>>(ResourceState.Idle)
    val generatedQrisCodeData = _generatedQrisCodeData.asStateFlow()

    private val _qrisStatusData =
        MutableStateFlow<ResourceState<QrisStatusUiModel>>(ResourceState.Idle)
    val qrisStatusData = _qrisStatusData.asStateFlow()

    fun scanQrisTransaction(qrCode: String) {
        viewModelScope.launch {
            scanQrisTransactionUseCase.invoke(qrCode)
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _scannedData.value = ResourceState.Loading
                        }

                        is ResourceState.Success -> {
                            _scannedData.value = ResourceState.Success(it.data)
                        }

                        is ResourceState.Error -> {
                            _scannedData.value = ResourceState.Error(it.exception)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun getUserAccount() {
        viewModelScope.launch {
            getUserAccountUseCase.invoke()
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _userRekeningData.value = ResourceState.Loading
                        }

                        is ResourceState.Success -> {
                            _userRekeningData.value = ResourceState.Success(it.data)
                        }

                        is ResourceState.Error -> {
                            _userRekeningData.value = ResourceState.Error(it.exception)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun confirmQrisMerhant(
        qrCode: String,
        accountNo: String,
        amount: Double,
        pin: String,
    ) {
        viewModelScope.launch {
            confirmQrisTransactionUseCase.confirmQrisMerhant(qrCode, accountNo, amount, pin)
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _merchantQrisPaymentTransactionData.value = ResourceState.Loading
                        }

                        is ResourceState.Success -> {
                            _merchantQrisPaymentTransactionData.value =
                                ResourceState.Success(it.data)
                        }

                        is ResourceState.Error -> {
                            _merchantQrisPaymentTransactionData.value =
                                ResourceState.Error(it.exception)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun confirmQrisReceivesFunds(
        qrCode: String,
        accountNo: String,
    ) {
        viewModelScope.launch {
            confirmQrisTransactionUseCase.confirmQrisReceivesFunds(qrCode, accountNo)
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _merchantQrisPaymentTransactionData.value = ResourceState.Loading
                        }

                        is ResourceState.Success -> {
                            _merchantQrisPaymentTransactionData.value =
                                ResourceState.Success(it.data)
                        }

                        is ResourceState.Error -> {
                            _merchantQrisPaymentTransactionData.value =
                                ResourceState.Error(it.exception)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun showQrisTransaction(accountNo: String, amount: Double, pin: String) {
        viewModelScope.launch {
            showQrisTransactionUseCase.generateQrisCode(
                accountNo = accountNo,
                amount = amount,
                pin = pin
            )
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _generatedQrisCodeData.value = ResourceState.Loading
                        }

                        is ResourceState.Success -> {
                            _generatedQrisCodeData.value = ResourceState.Success(it.data)
                        }

                        is ResourceState.Error -> {
                            _generatedQrisCodeData.value = ResourceState.Error(it.exception)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun getQrCodeStatus(qrCode: String) {
        viewModelScope.launch {
            showQrisTransactionUseCase.getQrStatus(qrCode)
                .collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            _qrisStatusData.value = ResourceState.Loading
                        }
                        is ResourceState.Success -> {
                            _qrisStatusData.value = ResourceState.Success(it.data)
                        }
                        is ResourceState.Error -> {
                            _qrisStatusData.value = ResourceState.Error(it.exception)
                        }
                        else -> {}
                    }
                }
        }

    }
}