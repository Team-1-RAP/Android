package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.colors_for_composable.Blue
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.colors_for_composable.DarkBlue
import com.team1.simplebank.colors_for_composable.LightBlue

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    placeholder: String,
    textValue: String,
    isCommonInputFields: Boolean = false,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = if (isCommonInputFields) {
            OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            )
        } else {
            OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Blue,
                unfocusedTextColor = LightBlue,
                cursorColor = Blue,
                focusedBorderColor = BlueNormal,
                unfocusedBorderColor = BlueNormal,
            )
        },
        shape = RoundedCornerShape(16.dp),
        value = textValue,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        placeholder = {
            Text(
                text = placeholder,
                color = if(isCommonInputFields) Color.Gray else LightBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = if (isPassword) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            KeyboardOptions.Default
        },
        leadingIcon = leadingIcon,
        visualTransformation = if (isPassword) {
            if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                val image =
                    if (passwordVisibility) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                val description = if (passwordVisibility) "sembunyikan password" else "lihat password"

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = BlueNormal,
                    )
                }
            }
        }
    )
}