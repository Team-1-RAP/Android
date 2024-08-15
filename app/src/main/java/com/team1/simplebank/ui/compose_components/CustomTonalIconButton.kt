package com.team1.simplebank.ui.compose_components

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun CustomTonalIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    talkBackLabel: String,
) {
    FilledTonalIconButton(
        onClick = { onClick() },
        modifier = modifier
            .semantics { contentDescription = talkBackLabel },
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}