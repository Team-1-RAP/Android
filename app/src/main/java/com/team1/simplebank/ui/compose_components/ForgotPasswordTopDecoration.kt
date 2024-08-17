package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.team1.simplebank.R

@Composable
fun ForgotPasswordTopDecoration(){
    Box(modifier = Modifier.fillMaxSize()){
        Image (
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.forgotpw_top_decoration),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
    }
}