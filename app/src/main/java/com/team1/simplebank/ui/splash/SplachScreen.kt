package com.team1.simplebank.ui.splash

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team1.simplebank.R
import com.team1.simplebank.ui.compose_components.GradientBackground
import com.team1.simplebank.ui.compose_components.OnBoardDecoration

@Composable
fun SplachScreen(){
    GradientBackground {
        OnBoardDecoration()
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(192.dp)
            )
        }
    }
}