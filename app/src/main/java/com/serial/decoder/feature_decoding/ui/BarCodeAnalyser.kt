package com.serial.decoder.feature_decoding.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.serial.decoder.core.util.canUpdate
import java.nio.ByteBuffer

class BarCodeAnalyser(
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
    private val onBarCodeNotFound: () -> Unit,
    private val onBarcodeFailed: (barcodes: Exception) -> Unit,
) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimeStamp = 0L

    @SuppressLint("UnsafeOptInUsageError") // because it's still experimental
    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (canUpdate(currentTimestamp, lastAnalyzedTimeStamp, 1)) {
            image.image?.let { imageToAnalyze ->
                val options = getBarScannerOptions()
                val barcodeScanner = BarcodeScanning.getClient(options)
                val imageToProcess =
                    InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)
                handleProcess(barcodeScanner, imageToProcess) {
                    image.close()
                }
            }

            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            image.close()
        }
    }

    private fun getBarScannerOptions(): BarcodeScannerOptions {
        return BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    private fun handleProcess(
        barcodeScanner: BarcodeScanner,
        imageToProcess: InputImage,
        onProcessCompletion: () -> Unit
    ) {
        barcodeScanner.process(imageToProcess)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    onBarcodeDetected(barcodes)
                } else {
                    onBarCodeNotFound()
                }
            }
            .addOnFailureListener { exception ->
                onBarcodeFailed(exception)
            }
            .addOnCompleteListener {
                onProcessCompletion()
            }

    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}

