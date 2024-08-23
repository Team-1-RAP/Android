package com.team1.simplebank.ui.qris

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.colors_for_composable.DarkBlue
import com.team1.simplebank.ui.compose_components.ButtonComponent

@Composable
fun QrisTransactionSuccessScreen(
    modifier: Modifier = Modifier,
    onBackToHome: () -> Unit = {},
    message: String,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(DarkBlue, Blue),
                        start = Offset(0.0f, 100f),
                        end = Offset(0f, 700.0f)
                    )
                )
        )

        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = modifier
                        .size(80.dp)
                )
                Text(
                    text = "Selamat!\nTransaksi anda berhasil",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                )
                HorizontalDivider(color = Color.Gray)
                Spacer(modifier = modifier.height(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.check_icon_blue),
                    contentDescription = null,
                    modifier = modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = message,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                )
                ButtonComponent(
                    onClick = { onBackToHome() },
                    label = "Berada",
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = BlueNormal,
                        contentColor = Color.White
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Home,
                            contentDescription = null
                        )
                    },
                    modifier = modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}