package com.team1.simplebank.ui.auth.forgotpswd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.ForgotPasswordTopDecoration

@Composable
fun ForgotPasswordConfirmationSuccessScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ForgotPasswordTopDecoration()
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .offset(y = 120.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.check_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(132.dp)
            )
            Spacer(modifier = modifier.height(80.dp))
            Text(
                text = "Password Berhasil Diubah",
                color = Color.Black,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Silakan gunakan kata sandi yang baru Anda buat saat melakukan login berikutnya",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
        ButtonComponent(
            onClick = { onNavigateToLogin() },
            label = "Kembali",
            buttonColor = ButtonDefaults.buttonColors(
                containerColor = BlueNormal,
                contentColor = Color.White
            ),
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .align(Alignment.BottomCenter)
        )

    }
}