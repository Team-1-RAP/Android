package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team1.simplebank.R
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.colors_for_composable.DarkBlue

@Composable
fun CustomTopAppBarForFeature(
    modifier: Modifier,
    title: @Composable () -> Unit,
    onBackPressed: () -> Unit,
    isBackEnable: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .fillMaxWidth()
            .height(92.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(DarkBlue, Blue),
                    start = Offset(0.0f, 0.0f),
                    end = Offset(0.0f, 250.0f)
                )
            )
            .padding(16.dp),
    ) {

        if (isBackEnable) {
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

        Box(
            modifier = modifier.align(Alignment.Center),
        ) {
            title()
        }
    }
}


@Preview
@Composable
fun CustomTopAppBarForFeaturePreview() {
    CustomTopAppBarForFeature(modifier = Modifier, title = {
        Image(
            painterResource(id = R.drawable.qris_logo_white),
            contentDescription = "Qris",
            modifier = Modifier
                .size(96.dp)
                .fillMaxWidth()
        )
    }, onBackPressed = {}, isBackEnable = true)
}