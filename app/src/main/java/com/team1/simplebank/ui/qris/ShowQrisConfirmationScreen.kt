package com.team1.simplebank.ui.qris

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.DropDownItem
import com.synrgy.xdomain.model.dropDownItem
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.CustomSnackbar
import com.team1.simplebank.ui.compose_components.DropDownMenuComponent
import com.team1.simplebank.ui.compose_components.LoadingScreen
import com.team1.simplebank.ui.compose_components.PinDialog
import com.team1.simplebank.ui.compose_components.TextFieldComponent

@Composable
fun ShowQrisConfirmationScreen(
    modifier: Modifier = Modifier,
    onConfirm: (String, String) -> Unit,
    onCancel: () -> Unit = {},
) {
    val qrisViewModel: QrisTransactionViewModel = hiltViewModel()
    val userData by qrisViewModel.userRekeningData.collectAsState()
    val generatedQrisCodeData by qrisViewModel.generatedQrisCodeData.collectAsState()

    var selectedDropDownItem by remember {
        mutableStateOf(DropDownItem("Pilih Rekening"))
    }

    var showPinDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorSnackbar by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(true) }
    var userAcount by remember { mutableStateOf(emptyList<AccountModel>()) }

    var nominal by remember { mutableStateOf("") }

    LaunchedEffect (Unit) {
        qrisViewModel.getUserAccount()
    }

    BackHandler {
        onCancel()
    }

    when (userData) {
        is ResourceState.Loading -> {
            showLoading = true
        }
        is ResourceState.Success -> {
            showLoading = false
            userAcount = (userData as ResourceState.Success<List<AccountModel>>).data
            when(generatedQrisCodeData) {
                is ResourceState.Loading -> {
                    showLoading = true
                }
                is ResourceState.Success -> {
                    showLoading = false
                    onConfirm(
                        (generatedQrisCodeData as ResourceState.Success).data.qrCode,
                        (generatedQrisCodeData as ResourceState.Success).data.timeOut
                    )
                }
                is ResourceState.Error -> {
                    showLoading = false
                    errorMessage = (generatedQrisCodeData as ResourceState.Error).exception.toString()
                    showErrorSnackbar = true
                }
                else  -> {}
            }
        }
        is ResourceState.Error -> {
            showLoading = false
            errorMessage = (userData as ResourceState.Error).exception.toString()
            showErrorSnackbar = true
        }
        else  -> {}
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
                    .padding(24.dp)
            ) {
                DropDownMenuComponent(
                    modifier = modifier,
                    dropDownItems = userAcount.map {
                        DropDownItem(it.noAccount)
                    },
                    onSelectedDropDownItem = { selectedDropDownItem = it },
                    selectedDropDownItem = selectedDropDownItem.noRekening
                )
                Spacer(modifier = modifier.height(24.dp))
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray.copy(alpha = 0.8f),
                    )
                ) {
                    Column(
                        modifier = modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "keterangan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Blue,
                        )
                        Text(
                            text = "1. Pilih rekening sumber\n2. Masukkan PIN Simple Bank\n3. Tunjukkan kode kepada penjual",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = modifier
                                .padding(top = 8.dp, start = 8.dp),
                        )
                    }
                }
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
                        } else if (nominal.isEmpty() ) {
                            showErrorSnackbar = true
                            errorMessage = "Masukkan nominal terlebih dahulu"
                        } else if (nominal.toDouble() > userAcount.find { it.noAccount == selectedDropDownItem.noRekening}?.balance!!) {
                            showErrorSnackbar = true
                            errorMessage = "Saldo tidak mencukupi"
                        } else {
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
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                )
                if (showErrorSnackbar) {
                    CustomSnackbar(
                        message = errorMessage,
                        onDismiss = { showErrorSnackbar = false },
                    )
                }
            }

            if (showPinDialog){
                PinDialog(
                    onDismiss = { showPinDialog = false },
                    onConfirm = { pin ->
                        showPinDialog = false
                        qrisViewModel.showQrisTransaction(selectedDropDownItem.noRekening, nominal.toDouble(),pin)
                    },
                    userPin = userAcount.find { it.noAccount == selectedDropDownItem.noRekening }?.pin!!,
                    modifier = modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

}