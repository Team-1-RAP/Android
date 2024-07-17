package com.team1.simplebank.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.colors_for_composable.DarkBlue
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.GradientBackground
import com.team1.simplebank.ui.compose_components.OnBoardDecoration
import com.team1.simplebank.ui.compose_components.TextFieldComponent

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showBiometricLoginBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    GradientBackground(
        modifier = modifier.fillMaxSize(),
        startColor = Color(0xFF8CACF9),
        endColor = Color(0xFFE7EDFB)
    ) {
        OnBoardDecoration()
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(140.dp)
                    .background(Color.Transparent)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = modifier.height(32.dp))
            Text(
                text = "Login akun Simple Bankmu",
                modifier = modifier.padding(bottom = 8.dp),
                color = DarkBlue,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                )
            )
            TextFieldComponent(
                modifier = modifier,
                placeholder = "Username",
                textValue = username,
                onValueChange = { username = it },
                isPassword = false,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User Icon",
                        modifier = modifier.size(24.dp)
                    )
                }
            )
            TextFieldComponent(
                modifier = modifier,
                placeholder = "Password",
                textValue = password,
                onValueChange = { password = it },
                isPassword = true,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.password_icon),
                        contentDescription = "Password Icon",
                        modifier = modifier.size(24.dp)
                    )
                }
            )
            Text(
                text = "Lupa Password?",
                color = DarkBlue,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 16.dp)
            )
            HorizontalDivider(modifier = modifier.fillMaxWidth(), color = BlueNormal)
            Spacer(modifier = modifier.height(120.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonComponent(
                    onClick = { /*TODO*/ },
                    label = "Login",
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = BlueNormal,
                        contentColor = Color.White
                    ),
                    modifier = modifier
                        .fillMaxWidth(fraction = 0.5f)
                )
                Spacer(modifier = modifier.width(8.dp))
                ButtonComponent(
                    onClick = { showBiometricLoginBottomSheet = true },
                    label = "Biometric",
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = BlueNormal
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Fingerprint,
                            contentDescription = "Fingerprint Icon",
                            modifier = modifier.size(24.dp).fillMaxWidth(fraction = 0.5f),
                        )
                    }
                )
            }

            if (showBiometricLoginBottomSheet) {
                BiometricLoginBottomSheet(
                    onDismissRequest = { showBiometricLoginBottomSheet = false },
                    sheetState = sheetState,
                    modifier = modifier,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricLoginBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Konfirmasi Sidik Jari",
                fontWeight = FontWeight(700),
                fontSize = 20.sp,
                color = BlueNormal
            )
            Text(
                text = "Sentuh sensor sidik jari",
                fontWeight = FontWeight(400),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(top = 4.dp)
            )
            Icon(
                imageVector = Icons.Rounded.Fingerprint,
                contentDescription = "Fingerprint Icon",
                tint = BlueNormal,
                modifier = modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 32.dp)
            )
            ButtonComponent(
                onClick = { onDismissRequest() },
                label = "Batalkan",
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Gray,
                )
            )
        }
    }
}

