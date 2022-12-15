package com.serial.decoder.feature_decoding.data.local.util

import com.serial.decoder.feature_decoding.data.local.entity.ManufactureEntity

interface DecodingUtility {
    fun isCorrectSerial(serial: String, brand: Brands): Boolean
    fun decodeSerial(serial: String, brand: Brands): ManufactureEntity
}