package com.team1.simplebank.ui.compose_components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.colors_for_composable.RedError
import kotlinx.coroutines.delay

@Composable
fun PinDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    userPin: String,
    modifier: Modifier,
) {
    val pinSize = 6

    BackHandler {
        onDismiss()
    }

    val inputPin = remember { mutableStateListOf<Int>() }
    var error by remember { mutableStateOf("") }

    if (inputPin.size == pinSize) {
        LaunchedEffect(true) {
            delay(300)
            if (inputPin.joinToString("") == userPin) {
                onConfirm(inputPin.joinToString(""))
            } else {
                inputPin.clear()
                error = "Pin tidak cocok"
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(16.dp, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp), clip = true)
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = modifier.height(32.dp))
                Text(
                    text = "Masukkan PIN rekening",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = modifier.height(16.dp))
                Row {
                    (0 until pinSize).forEach {
                        Icon(
                            imageVector = if (inputPin.size > it) Icons.Default.Circle else Icons.Outlined.Circle,
                            contentDescription = it.toString(),
                            modifier = modifier
                                .padding(8.dp)
                                .size(16.dp),
                            tint = Color.Black
                        )
                    }
                }
                Text(
                    text = error,
                    color = RedError,
                    modifier = modifier.padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.height(16.dp))
                Column(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(bottom = 20.dp)
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (1..3).forEach {
                            PinKeyItem(
                                onClick = { inputPin.add(it) }
                            ) {
                                Text(
                                    text = it.toString(),
                                    style = typography.titleLarge
                                )
                            }
                        }
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (4..6).forEach {
                            PinKeyItem(
                                onClick = { inputPin.add(it) }
                            ) {
                                Text(
                                    text = it.toString(),
                                    style = typography.titleLarge
                                )
                            }
                        }
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (7..9).forEach {
                            PinKeyItem(
                                onClick = { inputPin.add(it) }
                            ) {
                                Text(
                                    text = it.toString(),
                                    style = typography.titleLarge,
                                )
                            }
                        }
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            modifier = modifier
                                .size(24.dp)
                                .clickable {  }
                        )
                        PinKeyItem(
                            onClick = { inputPin.add(0) },

                        ) {
                            Text(
                                text = "0",
                                style = typography.titleLarge,
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Backspace,
                            contentDescription = "Clear",
                            modifier = modifier
                                .size(24.dp)
                                .clickable {
                                    if (inputPin.isNotEmpty()) {
                                        inputPin.removeLast()
                                    }
                                }
                        )
                    }
                }
            }

        }

    }

}

@Composable
fun PinKeyItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit,
) {

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick, role = Role.Button),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        content = {
            CompositionLocalProvider(
                LocalContentColor provides contentColor
            ) {
                ProvideTextStyle(
                    value = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    Box(
                        modifier = modifier.defaultMinSize(minWidth = 72.dp, minHeight = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        content()
                    }
                }
            }
        }
    )
}