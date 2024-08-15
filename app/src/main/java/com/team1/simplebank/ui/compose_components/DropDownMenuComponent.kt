package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.synrgy.xdomain.model.DropDownItem
import com.team1.simplebank.colors_for_composable.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuComponent(
    modifier: Modifier,
    dropDownItems: List<DropDownItem>,
    onSelectedDropDownItem: (DropDownItem) -> Unit,
    selectedDropDownItem: String,
) {
    var dropdownMenuExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = dropdownMenuExpanded,
        onExpandedChange = { dropdownMenuExpanded = !dropdownMenuExpanded },
        modifier = modifier
            .fillMaxWidth(),
    ) {

        OutlinedTextField(
            value = selectedDropDownItem,
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            trailingIcon = {
                if (dropdownMenuExpanded)
                    Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                else
                    Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Blue,
                unfocusedContainerColor = Blue,
                focusedBorderColor = Blue,
                unfocusedBorderColor = Blue,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                unfocusedPlaceholderColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
                focusedTrailingIconColor = Color.White,
                focusedPlaceholderColor = Color.White,
            ),
        )

        DropdownMenu(
            expanded = dropdownMenuExpanded,
            onDismissRequest = { dropdownMenuExpanded = false },
            modifier = modifier
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp, topStart = 0.dp, topEnd = 0.dp))
                .background(Color.White)
                .fillMaxWidth(0.865f)
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    modifier = modifier,
                    text = { Text(text = item.noRekening) },
                    onClick = {
                        onSelectedDropDownItem(item)
                        dropdownMenuExpanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.Black,
                    )
                )
            }
        }
    }
}