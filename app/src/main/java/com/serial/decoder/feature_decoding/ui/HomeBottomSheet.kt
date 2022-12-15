package com.serial.decoder.feature_decoding.ui

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.Barcode
import com.serial.decoder.R
import com.serial.decoder.core.*
import com.serial.decoder.core.util.*
import com.serial.decoder.ui.theme.TRIPLE_CORNER_DP
import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    bottomSheetState: BottomSheetState,
    onBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
    onBarcodeFailed: (exception: Exception) -> Unit,
    onBarcodeNotFound: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
    ) {
        PeekBar()
        ScanningSerialTextTitle(modifier)

        if (bottomSheetState.isExpanded) {
            CameraBox(
                modifier = modifier.testTag("CameraTag"),
                onBarcodeDetected = onBarcodeDetected,
                onBarcodeFailed = onBarcodeFailed,
                onBarcodeNotFound = onBarcodeNotFound,
            )
        } else {
            EmptyBox()
        }
    }
}

@Composable
fun PeekBar(modifier: Modifier = Modifier) {
    Row {
        NonDraggableBox(
            modifier
                .weight(1f)
        )
        BottomQRBox(modifier)
    }
}


@Composable
fun NonDraggableBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .draggable(orientation = Orientation.Vertical, state = rememberDraggableState {
                // We don't want the user to drag, thus ignore this behavior
            })
            .fillMaxWidth()
            .height(BOTTOM_ICON_CONTAINER_SIZE)
            .background(Color.Transparent)
    ) {
    }
}

@Composable
fun BottomQRBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = SPACING_QUADRUPLE)
            .clip(
                RoundedCornerShape(
                    topStart = TRIPLE_CORNER_DP,
                    topEnd = TRIPLE_CORNER_DP,
                )
            )
            .size(BOTTOM_ICON_CONTAINER_SIZE)
            .background(MaterialTheme.colors.secondary),


        contentAlignment = Alignment.BottomCenter
    )
    {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = stringResource(
                id = R.string.bottom_sheet_puller
            ),
            tint = Color.Unspecified
        )
    }
}


@Composable
fun ScanningSerialTextTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(
                start = SPACING_DOUBLE, end = SPACING_DOUBLE, bottom = SPACING_NORMAL
            ),
        text = stringResource(id = R.string.scan_serial_with_qr),
        style = MaterialTheme.typography.h3,
    )
}

@Composable
fun EmptyBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraBox(
    modifier: Modifier = Modifier,
    onBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
    onBarcodeFailed: (exception: Exception) -> Unit,
    onBarcodeNotFound: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)


    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifeCycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                cameraPermissionState.launchPermissionRequest()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose { lifeCycleOwner.lifecycle.removeObserver(observer) }
    })

    cameraPermissionState.HandlePermissionCases(modifier = modifier,
        ShouldShowRationaleContent = {
            ShouldShowRationaleContent(
                modifier = modifier,
                cameraPermissionState = cameraPermissionState
            )
        },
        PermissionDeniedPermanentlyContent = {
            PermissionDeniedPermanentContent(modifier = modifier)
        }) {
        CameraPreview(
            modifier = modifier,
            onBarcodeDetected = onBarcodeDetected,
            onBarcodeFailed = onBarcodeFailed,
            onBarcodeNotFound = onBarcodeNotFound
        )
    }


}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShouldShowRationaleContent(
    modifier: Modifier = Modifier,
    cameraPermissionState: PermissionState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                stringResource(
                    id = R.string.x_permission_is_required_for_scanning_qr, stringResource(
                        id = R.string.camera
                    )
                ),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = modifier.align(Alignment.CenterHorizontally),
                onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text(
                    text = stringResource(
                        id = R.string.request_x_permission, stringResource(
                            id = R.string.camera
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun PermissionDeniedPermanentContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(
                id = R.string.x_permission_is_denied,
                stringResource(id = R.string.camera)
            ), style = MaterialTheme.typography.h1, textAlign = TextAlign.Center
        )
    }
}


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
    onBarcodeFailed: (exception: Exception) -> Unit,
    onBarcodeNotFound: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var cameraProvider: ProcessCameraProvider? = null
    DisposableEffect(key1 = cameraProvider) {
        onDispose {
            cameraProvider?.let { it.unbindAll() }
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { androidViewContext -> initPreviewView(androidViewContext) },
        update = { previewView: PreviewView ->
            val cameraSelector: CameraSelector =
                buildCameraSelector(CameraSelector.LENS_FACING_BACK)
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)


            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()

                val preview = buildPreview().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }


                val barcodeAnalyser = BarCodeAnalyser(
                    onBarcodeDetected = onBarcodeDetected,
                    onBarcodeFailed = onBarcodeFailed,
                    onBarCodeNotFound = onBarcodeNotFound
                )
                val imageAnalysis: ImageAnalysis =
                    buildImageAnalysis(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }



                try {
                    cameraProvider?.let {
                        it.unbindAll() //Make sure we only use 1 use case related to camera

                        val camera = it.bindToLifecycle(
                            lifecycleOwner, cameraSelector, preview, imageAnalysis
                        )

                        camera.cameraControl.enableTorch(true) // TODO: Debug mode only
                    }


                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )


}


private fun initPreviewView(androidViewContext: Context): PreviewView {
    val previewView = PreviewView(androidViewContext).apply {
        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
    }
    return previewView
}

private fun buildPreview(): Preview {
    return Preview.Builder().build()
}

private fun buildImageAnalysis(imageAnalysisStrategy: Int): ImageAnalysis {
    return ImageAnalysis.Builder()
        .setBackpressureStrategy(imageAnalysisStrategy)
        .build()
}

private fun buildCameraSelector(cameraLens: Int): CameraSelector {
    return CameraSelector.Builder()
        .requireLensFacing(cameraLens)
        .build()
}