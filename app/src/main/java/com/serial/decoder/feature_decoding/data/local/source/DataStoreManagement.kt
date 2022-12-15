package com.serial.decoder.feature_decoding.data.local.source

import kotlinx.coroutines.flow.Flow

interface DataStoreManagement {
    fun getSerial(): Flow<String>
    suspend fun updateSerial(serial: String)
    fun getBrand(): Flow<String>
    suspend fun updateBrand(brand: String)
}