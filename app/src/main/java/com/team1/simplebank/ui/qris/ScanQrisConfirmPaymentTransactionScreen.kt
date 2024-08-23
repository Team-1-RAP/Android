package com.team1.simplebank.ui.qris

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.DropDownItem
import com.synrgy.xdomain.model.ScanQrisUiModel
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.Converter.toRupiah
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.CustomSnackbar
import com.team1.simplebank.ui.compose_components.DropDownMenuComponent
import com.team1.simplebank.ui.compose_components.LoadingScreen
import com.team1.simplebank.ui.compose_components.PinDialog
import com.team1.simplebank.ui.compose_components.TextFieldComponent
import kotlinx.coroutines.delay


@Composable
fun ScanQrisConfirmPaymentTransactionScreen(
    modifier: Modifier = Modifier,
    qrValue: String,
    onSuccess: (String) -> Unit,
    onError: () -> Unit,
) {
    val qrisViewModel: QrisTransactionViewModel = hiltViewModel()
    val scannedData by qrisViewModel.scannedData.collectAsState()
    val userData by qrisViewModel.userRekeningData.collectAsState()
    val merchantQrisPaymentTransactionData by qrisViewModel.merchantQrisPaymentTransactionData.collectAsState()

    var nominal by remember { mutableStateOf("") }
    var selectedDropDownItem by remember { mutableStateOf(DropDownItem("Pilih Rekening")) }
    var showErrorSnackbar by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var dataScanned by remember {
        mutableStateOf(
            ScanQrisUiModel(
                senderName = "",
                amount = null,
                qrCode = ""
            )
        )
    }
    var userDataObtained by remember { mutableStateOf(emptyList<AccountModel>()) }

    LaunchedEffect(Unit) {
        qrisViewModel.scanQrisTransaction(qrValue)
        qrisViewModel.getUserAccount()
    }

    LaunchedEffect(scannedData, userData, merchantQrisPaymentTransactionData) {
        when (scannedData) {
            is ResourceState.Loading -> {
                showLoading = true
            }

            is ResourceState.Success -> {
                when (userData) {
                    is ResourceState.Loading -> {
                        showLoading = true
                    }

                    is ResourceState.Success -> {
                        showLoading = false
                        dataScanned = (scannedData as ResourceState.Success).data
                        userDataObtained = (userData as ResourceState.Success).data
                        when (merchantQrisPaymentTransactionData) {
                            is ResourceState.Loading -> {
                                showLoading = true
                            }

                            is ResourceState.Success -> {
                                showLoading = true
                                onSuccess(
                                    "Anda telah membayar sebesar Rp${(merchantQrisPaymentTransactionData as ResourceState.Success).data.amount.toRupiah()}" +
                                            " melalui fitur QRIS kepada ${(merchantQrisPaymentTransactionData as ResourceState.Success).data.name}"
                                )
                            }

                            is ResourceState.Error -> {
                                showLoading = false
                                showErrorSnackbar = true
                                errorMessage ="Terjadi Kesalahan, harap coba lagi nanti"
                            }

                            else -> {
                            }
                        }
                    }

                    is ResourceState.Error -> {
                        showLoading = false
                        showErrorSnackbar = true
                        errorMessage =  "Kode Qris tidak Valid"
                        delay(1000L)
                        onError()
                    }

                    else -> {
                        showLoading = true
                    }
                }
            }

            is ResourceState.Error -> {
                showLoading = false
                showErrorSnackbar = true
                errorMessage = (scannedData as ResourceState.Error).exception
                    ?: "Terjadi Kesalahan, harap coba lagi nanti"
            }

            else -> {
                showLoading = true
            }
        }
    }

    if (showLoading) {
        LoadingScreen()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    text = "Kirim ke",
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Blue,
                        contentColor = Color.White
                    )
                ) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = dataScanned.senderName,
                            modifier = modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = modifier.height(24.dp))
                DropDownMenuComponent(
                    modifier = modifier.semantics {
                        contentDescription = "pilih sumber rekening"
                    },
                    onSelectedDropDownItem = { selectedDropDownItem = it },
                    dropDownItems = userDataObtained.map {
                        DropDownItem(it.noAccount)
                    },
                    selectedDropDownItem = selectedDropDownItem.noRekening
                )
                Spacer(modifier = modifier.height(24.dp))
                TextFieldComponent(
                    placeholder = "Masukkan nominal",
                    textValue = nominal,
                    onValueChange = { nominal = it },
                    isCommonInputFields = true,
                    modifier = modifier.semantics { contentDescription = "masukkan nominal" }
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                ButtonComponent(
                    onClick = {
                        if (selectedDropDownItem.noRekening == "Pilih Rekening") {
                            showErrorSnackbar = true
                            errorMessage = "Pilih rekening terlebih dahulu"
                        } else if (nominal.isEmpty()) {
                            showErrorSnackbar = true
                            errorMessage = "Silakan masukan nominal"
                        } else if (nominal.toInt() > userDataObtained.find { it.noAccount == selectedDropDownItem.noRekening }?.balance!!) {
                            showErrorSnackbar = true
                            errorMessage =
                                "Pastikan saldo anda cukup untuk melakukan pembayaran"
                        } else if (nominal.toInt() <= 10000) {
                            showErrorSnackbar = true
                            errorMessage =
                                "Nominal transaksi minimal Rp10.000"
                        }else {
                            showPinDialog = true
                        }
                    },
                    label = "Selanjutnya",
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = Color.White,
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
                )
                if (showErrorSnackbar) {
                    CustomSnackbar(
                        message = errorMessage,
                        onDismiss = { showErrorSnackbar = false },
                    )
                }
            }
            if (showPinDialog) {
                PinDialog(
                    onDismiss = { showPinDialog = false },
                    onConfirm = { pin ->
                        showLoading = true
                        showPinDialog = false
                        qrisViewModel.confirmQrisMerhant(
                            qrCode = qrValue,
                            accountNo = selectedDropDownItem.noRekening,
                            amount = nominal.toDouble(),
                            pin = pin
                        )
                    },
                    userPin = userDataObtained.find { it.noAccount == selectedDropDownItem.noRekening }?.pin
                        ?: "",
                    modifier = modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}