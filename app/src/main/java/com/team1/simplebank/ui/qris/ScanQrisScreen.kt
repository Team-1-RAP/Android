package com.team1.simplebank.ui.qris

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.Panorama
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.CustomTonalIconButton

@Preview(showBackground = true)
@Composable
fun ScanQrisScreen(
    modifier: Modifier = Modifier,
){

    val context = LocalContext.current
    var isButtonGalleryActive by remember {
        mutableStateOf(false)
    }
    var isButtonFlashActive by remember {
        mutableStateOf(false)
    }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        isButtonGalleryActive = false
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Arahkan kamera ke kode QRIS untuk melakukan pembayaran",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Card (
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ){}

        CustomTonalIconButton(
            modifier = modifier
                .padding(start = 48.dp, bottom =24.dp)
                .align(Alignment.BottomStart),
            icon = if (isButtonGalleryActive) Icons.Filled.Panorama else Icons.Outlined.Panorama,
            onClick = {
                galleryLauncher.launch("image/*")
                isButtonGalleryActive = true
            },
            talkBackLabel = "ambil dari galeri",
            containerColor = if (isButtonGalleryActive) BlueNormal else Color.White,
            contentColor = if (isButtonGalleryActive) Color.White else BlueNormal
        )

        CustomTonalIconButton(
            modifier = modifier
                .padding(end = 48.dp, bottom =24.dp)
                .align(Alignment.BottomEnd),
            icon = if (isButtonFlashActive) Icons.Filled.FlashOn else Icons.Outlined.FlashOff,
            onClick = {
                isButtonFlashActive = !isButtonFlashActive
                toggleFlashlight(context, isButtonFlashActive)
            },
            talkBackLabel = "ambil dari galeri",
            containerColor = if (isButtonFlashActive) BlueNormal else Color.White,
            contentColor = if (isButtonFlashActive) Color.White else BlueNormal
        )
    }
}

private fun toggleFlashlight(context: Context, turnOn: Boolean) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    try {
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, turnOn)
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}