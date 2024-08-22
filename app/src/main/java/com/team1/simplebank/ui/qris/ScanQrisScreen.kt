package com.team1.simplebank.ui.qris

import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.Panorama
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.team1.simplebank.colors_for_composable.BlueNormal
import com.team1.simplebank.ui.compose_components.CustomTonalIconButton
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors


@Composable
fun ScanQrisScreen(
    modifier: Modifier = Modifier,
    onQrCodeScanned: (String) -> Unit,
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
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

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }

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
        Box(
            modifier = modifier
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .align(Alignment.BottomCenter),
        ) {
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProvider = cameraProviderFuture.get()
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val resolutionSelector = ResolutionSelector.Builder()
                        .setAllowedResolutionMode(ResolutionSelector.PREFER_HIGHER_RESOLUTION_OVER_CAPTURE_RATE)
                        .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)
                        .build()
                    val preview = androidx.camera.core.Preview.Builder()
                        .setResolutionSelector(resolutionSelector)
                        .build()
                        .also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                    val imageAnalyzr = ImageAnalysis.Builder()
                        .setResolutionSelector(resolutionSelector)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(executor) { imageProxy ->
                                processImageProxy(
                                    imageProxy,
                                    onQrCodeScanned,
                                )
                            }
                        }
                    try {
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                android.Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraProvider.unbindAll()
                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalyzr
                            )
                            camera.cameraControl.enableTorch(isButtonFlashActive)
                        }
                    } catch (exc: Exception) {
                        exc.printStackTrace()
                    }
                    previewView
                }
            )
        }

        CustomTonalIconButton(
            modifier = modifier
                .padding(start = 48.dp, bottom = 24.dp)
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
                .padding(end = 48.dp, bottom = 24.dp)
                .align(Alignment.BottomEnd),
            icon = if (isButtonFlashActive) Icons.Filled.FlashOn else Icons.Outlined.FlashOff,
            onClick = {
                isButtonFlashActive = !isButtonFlashActive
                val cameraProvider = cameraProviderFuture.get()
                val camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA
                )
                camera.cameraControl.enableTorch(isButtonFlashActive)
            },
            talkBackLabel = "flash",
            containerColor = if (isButtonFlashActive) BlueNormal else Color.White,
            contentColor = if (isButtonFlashActive) Color.White else BlueNormal
        )
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    onQrCodeScanned: (String) -> Unit,
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val option = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(option)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    imageProxy.close()
                    barcodes.first().rawValue?.let {
                        println("QR Code Value: $it")
                        if (it.startsWith("http") || it.contains("/")) {
                            val value = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                            onQrCodeScanned(value)
                        } else {
                            onQrCodeScanned(it)
                        }
                    }
                }

            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}