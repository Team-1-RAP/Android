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
import androidx.compose.ui.text.font.FontWeight
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

@Preview(showBackground = true)
@Composable
fun ScanQrisConfirmReceivePaymentTransactionScreen(
    modifier: Modifier = Modifier,
    qrValue: String = "qrValue",
    onSuccess: () -> Unit = {},
) {
    var selectedDropDownItem by remember { mutableStateOf(DropDownItem("Pilih rekening penyimpanan")) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorSnackbar by remember { mutableStateOf(false) }

    val pengirim  = "Nama pengirim"
    val jumlah  = "Rp. 10.000"

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
            Text(
                text = "Rekening penyimpanan",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp)
            )
            DropDownMenuComponent(
                modifier = modifier,
                dropDownItems = dropDownItem,
                onSelectedDropDownItem = { selectedDropDownItem = it },
                selectedDropDownItem = selectedDropDownItem.noRekening
            )
            Spacer(modifier = modifier.height(24.dp))
            Text(
                text = "Pengirim",
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
                        text = pengirim,
                        modifier = modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Text(
                text = "Jumlah",
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
                        text = jumlah,
                        modifier = modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
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
                        text = "1. Pastikan jumlah nominal benar\n2. Tekan terima uang untuk menerima uang",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = modifier
                            .padding(top = 8.dp, start = 8.dp),
                    )
                }
            }
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
                    } else {
                        // confirm pin
                        onSuccess()
                    }
                },
                label = "Terima uang",
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
    }
}