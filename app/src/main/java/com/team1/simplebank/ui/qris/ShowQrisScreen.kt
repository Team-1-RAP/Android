package com.team1.simplebank.ui.qris

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.team1.simplebank.colors_for_composable.Blue
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun ShowQrisScreen(
    modifier: Modifier = Modifier,
    totalTime: Int = 60 * 5,
    onCountDownFinished: () -> Unit = {},
) {

    var timeLeft by remember {
        mutableIntStateOf(totalTime)
    }

    val minutes = (timeLeft / 60).toString().padStart(2, '0')
    val seconds = (timeLeft % 60).toString().padStart(2, '0')

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
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
            //async image
            Image(
                painter = rememberAsyncImagePainter(model = "https://static.vecteezy.com/system/resources/previews/013/722/213/non_2x/sample-qr-code-icon-png.png"),
                contentDescription = "Qris",
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(Color.White)
            )
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