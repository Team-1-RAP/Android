package com.team1.simplebank.ui.qris

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.CustomSnackbar


data class QrisTabItems(
    var title: String,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun QrisInitialScreen(
    modifier: Modifier = Modifier,
) {
    val qrisTabItems = listOf(
        QrisTabItems("scan kode"),
        QrisTabItems("tampilkan kode"),
    )

    val pagerState = rememberPagerState { qrisTabItems.size }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    var showErrorSnackbar by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var hasCameraPermission by remember { mutableStateOf(false) }

    if (showErrorSnackbar) {
        Box(modifier = modifier.fillMaxSize()) {
            CustomSnackbar(
                message = errorMessage,
                onDismiss = { showErrorSnackbar = false },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = modifier.fillMaxHeight(0.9f),
            state = pagerState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) { pageIndex ->
            when (pageIndex) {
                0 -> {
                    if (hasCameraPermission) {
                        ScanQrisScreen()
                    } else {
                        RequestCameraPermission(
                            onPermissionGranted = { hasCameraPermission = true },
                            onPermissionDenied = {
                                showErrorSnackbar = true; errorMessage = "Camera permission denied"
                            }
                        )
                    }
                }

                1 -> {
                    ShowQrisScreen(
                        modifier = modifier,
                        totalTime = 300,
                        onCountDownFinished = { selectedTabIndex = 0 }
                    )
                }
            }
        }
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
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
                    onClick = { selectedTabIndex = index },
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
