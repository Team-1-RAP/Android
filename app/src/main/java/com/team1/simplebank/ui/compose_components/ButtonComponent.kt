package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.colors_for_composable.BlueNormal

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    buttonColor: ButtonColors,
    leadingIcon: @Composable (() -> Unit)? = null,
    isBordered: Boolean = false,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = buttonColor,
        modifier = modifier,
        border = if (isBordered) {
            BorderStroke(
                width = 2.dp,
                color = BlueNormal
            )
        } else {
            null
        }
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.let { icon ->
                icon()
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight(700)
            )
        }
    }
}