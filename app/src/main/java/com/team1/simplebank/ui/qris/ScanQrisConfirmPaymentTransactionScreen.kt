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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synrgy.xdomain.model.DropDownItem
import com.synrgy.xdomain.model.dropDownItem
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.CustomSnackbar
import com.team1.simplebank.ui.compose_components.DropDownMenuComponent
import com.team1.simplebank.ui.compose_components.TextFieldComponent


@Preview(showBackground = true)
@Composable
fun ScanQrisConfirmPaymentTransactionScreen(
    modifier: Modifier = Modifier,
    qrValue: String = "qrValue",
    onSuccess: () -> Unit = {},

) {

    var nominal by remember { mutableStateOf("") }

    var selectedDropDownItem by remember {
        mutableStateOf(DropDownItem("Pilih rekening sumber"))
    }

    var showErrorSnackbar by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

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
                        text = qrValue,
                        modifier = modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            DropDownMenuComponent(
                modifier = modifier.semantics { contentDescription = "pilih sumber rekening" },
                onSelectedDropDownItem = { selectedDropDownItem = it },
                dropDownItems = dropDownItem,
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

        Column (
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
                    } else if (nominal.toInt() > 10000) {
                        showErrorSnackbar = true
                        errorMessage = "Pastikan saldo anda cukup untuk melakukan pembayaran"
                    } else {
                        //TODO: send data to server : POST > navigate to next screen
                        onSuccess()
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
    }
}