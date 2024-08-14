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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun ShowQrisConfirmationScreen(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onCancel: (Int) -> Unit = {},
) {

    var selectedDropDownItem by remember {
        mutableStateOf(DropDownItem("Pilih Rekening"))
    }

    var errorMessage by remember { mutableStateOf("") }
    var showErrorSnackbar by remember { mutableStateOf(false) }

    BackHandler {
        onCancel(0)
    }

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
                dropDownItems = dropDownItem,
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
                        onConfirm()
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
    }

}