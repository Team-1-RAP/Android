package com.team1.simplebank.ui.auth.forgotpswd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.ForgotPasswordTopDecoration
import com.team1.simplebank.ui.compose_components.TextFieldComponent

@Preview(showBackground = true)
@Composable
fun ForgotPasswordInputBirthDateScreen(
    modifier: Modifier = Modifier,
    onNavigateToInputEmail: () -> Unit = {},
) {

    var tanggalLahir by remember { mutableStateOf("") }
    var bulanLahir by remember { mutableStateOf("") }
    var tahunLahir by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ForgotPasswordTopDecoration()
        Column(
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(132.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Tanggal Lahir Anda",
                color = BlueNormal,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Masukan tanggal lahir untuk mengonfirmasi identitas Anda.",
                modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
                fontSize = 16.sp
            )
            Row(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                TextFieldComponent(
                    modifier = modifier.weight(0.3f),
                    placeholder = "DD",
                    textValue = tanggalLahir,
                    onValueChange = { tanggalLahir = it },
                    isCommonInputFields = true,
                )
                Spacer(modifier = modifier.weight(0.05f))
                TextFieldComponent(
                    modifier = modifier.weight(0.3f),
                    placeholder = "MM",
                    textValue = bulanLahir,
                    onValueChange = { bulanLahir = it },
                    isCommonInputFields = true,
                )
                Spacer(modifier = modifier.weight(0.05f))
                TextFieldComponent(
                    modifier = modifier.weight(0.3f),
                    placeholder = "YYYY",
                    textValue = tahunLahir,
                    onValueChange = { tahunLahir = it },
                    isCommonInputFields = true,
                )
            }
            Spacer(modifier = modifier.height(56.dp))
            ButtonComponent(
                onClick = { onNavigateToInputEmail() },
                label = "Selanjutnya",
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = BlueNormal,
                    contentColor = Color.White
                )
            )
        }
    }
}