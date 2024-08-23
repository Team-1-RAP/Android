package com.team1.simplebank.ui.qris

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.ui.compose_components.LoadingScreen
import com.team1.simplebank.ui.compose_components.qrBitmapPainter
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ShowQrisScreen(
    modifier: Modifier = Modifier,
    validUntil: String,
    qrValue: String,
    onCountDownFinished: () -> Unit,
    onSuccess: (String) -> Unit,
) {

    val qrisViewModel: QrisTransactionViewModel = hiltViewModel()
    val qrisStatusData by qrisViewModel.qrisStatusData.collectAsState()

    val bitmap = qrBitmapPainter(qrValue)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    val validUntilDateTime = LocalDateTime.parse(validUntil, formatter)
    val now = LocalDateTime.now()
    val timeLeftInSeconds = Duration.between(now, validUntilDateTime).seconds.toInt()
    var timeLeft by remember {
        mutableIntStateOf(timeLeftInSeconds)
    }
    val minutes = (timeLeft / 60).toString().padStart(2, '0')
    val seconds = (timeLeft % 60).toString().padStart(2, '0')

    var showLoading by remember { mutableStateOf(false) }

    when(qrisStatusData) {
        is ResourceState.Success -> {
            showLoading = true
            onSuccess(
                "Anda telah membayar sebesar Rp. ${(qrisStatusData as ResourceState.Success).data.amount} " +
                        " melalui fitur QRIS kepada ${(qrisStatusData as ResourceState.Success).data.name}"
            )
        }
        is ResourceState.Error -> {
            Log.d("QrisTransactionScreen", "Error: ${(qrisStatusData as ResourceState.Error).exception}")
        }

        else -> {}
    }

    LaunchedEffect (bitmap) {
        showLoading = bitmap == null
    }

    if (showLoading) {
        LoadingScreen()
    } else {
        LaunchedEffect(timeLeft) {
            if (timeLeft > 0) {
                delay(1000L)
                qrisViewModel.getQrCodeStatus(qrValue)
                timeLeft--
            } else {
                onCountDownFinished()
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Tunjukkan kode kepada penjual untuk melakukan pembayaran",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = modifier.height(56.dp))
            Box(
                modifier = modifier
                    .size(244.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Blue),
                contentAlignment = Alignment.Center,
            ) {
                if (bitmap != null) {
                    Image(
                        painter = remember(bitmap) { BitmapPainter(bitmap.asImageBitmap()) },
                        contentDescription = "Qris",
                        modifier = Modifier,
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
            Spacer(modifier = modifier.height(32.dp))
            Text(
                text = "Kode akan berakhir pada",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = modifier,
                text = "$minutes : $seconds",
                fontSize = 24.sp,
                color = Blue,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}