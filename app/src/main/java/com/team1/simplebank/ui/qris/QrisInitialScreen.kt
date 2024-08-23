package com.team1.simplebank.ui.qris

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.CustomSnackbar
import com.team1.simplebank.ui.compose_components.CustomTopAppBarForFeature
import com.team1.simplebank.ui.compose_components.LoadingScreen
import kotlinx.coroutines.delay


data class QrisTabItems(
    var title: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrisInitialScreen(
    modifier: Modifier = Modifier,
    onQrPaymentCodeValueObtained: (String) -> Unit,
    onQrReceivePaymentCodeValueObtained: (String) -> Unit,
    onSuccess: (String) -> Unit,
) {
    val qrisTabItems = listOf(
        QrisTabItems("scan kode"),
        QrisTabItems("tampilkan kode"),
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    var showErrorSnackbar by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var isConfirmShowQrisPassed by remember { mutableStateOf(false) }
    var showConfirmScreenForShowingQris by remember { mutableStateOf(false) }
    var isCameraActive by remember { mutableStateOf(true) }
    var generatedQrValue by remember { mutableStateOf("") }
    var qrTimeout by remember { mutableStateOf("") }

    if (showErrorSnackbar) {
        Box(modifier = modifier
            .fillMaxSize()
            .zIndex(2f)) {
            CustomSnackbar(
                message = errorMessage,
                onDismiss = { showErrorSnackbar = false },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }

    LaunchedEffect(showConfirmScreenForShowingQris) {
        if (!showConfirmScreenForShowingQris) {
            isCameraActive = true
        }
    }

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 0) {
            isConfirmShowQrisPassed = false
        } else {
            isConfirmShowQrisPassed = true
            showConfirmScreenForShowingQris = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier.fillMaxHeight(0.9f),
        ) {
            when (selectedTabIndex) {
                0 -> {
                    isConfirmShowQrisPassed = false
                    if (hasCameraPermission) {
                        if (isCameraActive) {
                            ScanQrisScreen(
                                modifier = modifier,
                                onQrCodeScanned = {
                                    if (it.contains("ACC")) {
                                        onQrReceivePaymentCodeValueObtained(it)
                                    } else if (it.contains("MRCHNT")) {
                                        onQrPaymentCodeValueObtained(it)
                                    } else {
                                        showErrorSnackbar = true
                                        errorMessage = "Kode QR tidak valid"
                                    }
                                }
                            )
                        }
                    } else {
                        RequestCameraPermission(
                            onPermissionGranted = { hasCameraPermission = true },
                            onPermissionDenied = {
                                showErrorSnackbar = true; errorMessage =
                                "Anda membutuhkan akses kamera untuk menggunakan fitur ini"
                            }
                        )
                    }
                }

                1 -> {
                    if (isConfirmShowQrisPassed) {
                        ShowQrisScreen(
                            modifier = modifier,
                            qrValue = generatedQrValue,
                            onCountDownFinished = { selectedTabIndex = 0 },
                            onSuccess = { message ->
                                onSuccess(message)
                            },
                            validUntil = qrTimeout,
                        )
                    }
                }
            }
        }
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier.background(Color.White),
            divider = {},
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = modifier.tabIndicatorOffset(
                        selectedTabIndex,
                    ),
                    width = 96.dp,
                    height = 4.dp,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                    color = BlueNormal
                )
            }
        ) {
            qrisTabItems.forEachIndexed { index, qrisTabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    modifier = modifier
                        .background(Color.White)
                        .semantics {
                            contentDescription =
                                qrisTabItem.title
                        },
                    onClick = {
                        if (index == 1) {
                            showConfirmScreenForShowingQris = true
                            return@Tab
                        } else {
                            selectedTabIndex = index
                        }

                    },
                    text = {
                        Text(
                            text = qrisTabItem.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selectedContentColor = BlueNormal,
                    unselectedContentColor = Color.Gray
                )
            }
        }
    }

    if (showConfirmScreenForShowingQris) {
        isCameraActive = false

        var showLoading by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            delay(1000L)
            showLoading = false
        }

        Dialog(
            onDismissRequest = { showConfirmScreenForShowingQris = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            )
        ) {
            Card(
                modifier = modifier
                    .fillMaxSize()
                    .padding(0.dp),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                ) {
                    CustomTopAppBarForFeature(
                        modifier = modifier,
                        title = {
                            Image(
                                painterResource(id = R.drawable.qris_logo_white),
                                contentDescription = "Qris",
                                modifier = Modifier
                                    .size(80.dp)
                                    .fillMaxWidth(),
                            )
                        },
                        onBackPressed = { showConfirmScreenForShowingQris = false }
                    )
                    if (showLoading) {
                        LoadingScreen()
                    } else {
                        ShowQrisConfirmationScreen(
                            modifier = modifier,
                            onConfirm = { qrValue, timeOut ->
                                generatedQrValue = qrValue
                                qrTimeout = timeOut
                                selectedTabIndex = 1
                            },
                            onCancel = {
                                showConfirmScreenForShowingQris = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val ctx = LocalContext.current
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                ctx,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        } else {
            activityResultLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}

