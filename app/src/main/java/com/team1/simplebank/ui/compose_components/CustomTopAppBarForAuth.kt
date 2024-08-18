package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.Blue


@Composable
fun CustomTopAppBarForAuth(
    modifier: Modifier,
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.forgotpw_topappbar_bg),
            contentDescription = null,
            modifier = modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        IconButton(
            onClick = { onBackPressed() },
            modifier = modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "kembali",
                modifier = modifier.size(32.dp),
                tint = Color.White
            )
        }
    }
}