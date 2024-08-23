package com.team1.simplebank.ui.auth.forgotpswd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.ForgotPasswordTopDecoration
import com.team1.simplebank.ui.compose_components.TextFieldComponent

@Composable
fun ForgotPasswordInputEmailScreen(
    modifier: Modifier = Modifier,
    onNavigateToInputOtpCode: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }

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
                text = "Masukkan email Anda",
                color = BlueNormal,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Masukan email Anda untuk verifikasi akun menggunakan kode OTP.",
                modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
                fontSize = 16.sp
            )
            TextFieldComponent(
                placeholder = "Email",
                textValue = email,
                onValueChange = { email = it },
                isCommonInputFields = true,
            )

        }
        ButtonComponent(
            onClick = { onNavigateToInputOtpCode(email) },
            label = "Selanjutnya",
            buttonColor = ButtonDefaults.buttonColors(
                containerColor = BlueNormal,
                contentColor = Color.White
            ),
            modifier = modifier.align(Alignment.BottomCenter).padding(vertical = 32.dp, horizontal = 16.dp)
        )
    }
}
