package com.team1.simplebank.ui.splash

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.R
import com.team1.simplebank.ui.compose_components.GradientBackground
import com.team1.simplebank.ui.compose_components.OnBoardDecoration

@Preview(showBackground = true)
@Composable
fun SplachScreen(){
    GradientBackground {
        OnBoardDecoration()
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(224.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = "Copyright © 2024 - Simple Bank",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}