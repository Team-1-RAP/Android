package com.team1.simplebank.ui.qris

import com.synrgy.xdomain.useCase.qris.ConfirmQrisTransactionUseCase
import com.synrgy.xdomain.useCase.qris.ScanQrisTransactionUseCase
import com.synrgy.xdomain.useCase.qris.ShowQrisTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrisTransactionViewModel @Inject constructor(
    private val scanQrisTransactionUseCase: ScanQrisTransactionUseCase,
    private val showQrisTransactionUseCase: ShowQrisTransactionUseCase,
    private val confirmQrisTransactionUseCase: ConfirmQrisTransactionUseCase
) {

}