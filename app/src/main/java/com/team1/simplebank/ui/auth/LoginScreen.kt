package com.team1.simplebank.ui.auth

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.colors_for_composable.RedError
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.ui.HomeActivity
import com.team1.simplebank.ui.compose_components.ButtonComponent
import com.team1.simplebank.ui.compose_components.CustomSnackbar
import com.team1.simplebank.ui.compose_components.GradientBackground
import com.team1.simplebank.ui.compose_components.LoadingScreen
import com.team1.simplebank.ui.compose_components.OnBoardDecoration
import com.team1.simplebank.ui.compose_components.TextFieldComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onForgotPasswordButtonClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showBiometricLoginBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val viewModel: LoginViewModel = hiltViewModel()
    val authData by viewModel.authData.collectAsState()

    var showErrorSnackbar by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    fun login(usrname: String, pass: String) {
        viewModel.login(usrname, pass)
    }

    LaunchedEffect(authData) {
        when (authData) {
            is ResourceState.Loading -> {
                // Show loading screen
            }

            is ResourceState.Success -> {
                context.startActivity(Intent(context, HomeActivity::class.java))
                (context as Activity).finish()
            }

            is ResourceState.Error -> {
                errorMessage =
                    "Silakan periksa kembali username dan password Anda"
                showErrorSnackbar = true
            }

            else -> {}
        }
    }

    if (authData is ResourceState.Loading) {
        LoadingScreen()
    } else {
        GradientBackground(
            modifier = Modifier
                .fillMaxSize(),
            startColor = Color.White,
            endColor = Color.White
        ) {
            OnBoardDecoration()
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.Transparent)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "LOGIN akun Simple Bankmu",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .semantics { contentDescription = "login akun simple bankmu" },
                    color = BlueNormal,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                    )
                )
                TextFieldComponent(
                    modifier = Modifier
                        .focusable()
                        .semantics { contentDescription = "username" },
                    placeholder = "Username",
                    textValue = username,
                    onValueChange = { username = it },
                    isPassword = false,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = "User Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
                TextFieldComponent(
                    modifier = Modifier
                        .focusable()
                        .semantics { contentDescription = "password" },
                    placeholder = "Password",
                    textValue = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.password_icon),
                            contentDescription = "Password Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )

                TextButton(
                    onClick = {
                        onForgotPasswordButtonClicked()
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(vertical = 16.dp)
                        .semantics { contentDescription = "lupa password" }
                ) {

                    Text(
                        text = "Lupa Password?",
                        color = BlueNormal,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                        ),
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = BlueNormal)
                Spacer(modifier = Modifier.height(120.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonComponent(
                        onClick = {
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                login(username, password)
                            } else {
                                showErrorSnackbar = true
                                errorMessage = "Username dan password tidak boleh kosong"
                            }
                        },
                        label = "Login",
                        buttonColor = ButtonDefaults.buttonColors(
                            containerColor = BlueNormal,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.5f)
                            .semantics { contentDescription = "Tombol Login" }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ButtonComponent(
                        onClick = { showBiometricLoginBottomSheet = true },
                        label = "Biometrik",
                        buttonColor = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = BlueNormal,
                        ),
                        isBordered = true,
                        modifier = Modifier.semantics {
                            contentDescription = "Tombol login biometrik"
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Fingerprint,
                                contentDescription = "Fingerprint Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .fillMaxWidth(fraction = 0.5f),
                            )
                        }
                    )
                }

                if (showBiometricLoginBottomSheet) {
                    BiometricLoginBottomSheet(
                        onDismissRequest = { showBiometricLoginBottomSheet = false },
                        sheetState = sheetState,
                        modifier = Modifier,
                    )
                }

            }
            if (showErrorSnackbar) {
                CustomSnackbar(
                    message = errorMessage,
                    onDismiss = { showErrorSnackbar = false },
                    modifier = Modifier.align(Alignment.BottomCenter),
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
                color = RedError,
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
                tint = Color.Gray,
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
                ),
                modifier = modifier
                    .semantics { contentDescription = "Tombol Batalkan Biometric Login" }
            )
        }
    }
}

