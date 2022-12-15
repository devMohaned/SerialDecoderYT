package com.serial.decoder.feature_decoding.data.local.source

import com.serial.decoder.feature_decoding.data.local.util.Brands
import kotlinx.coroutines.flow.*

class FakeSerialDecoderDataStore : DataStoreManagement {
    private var serialFlow: MutableStateFlow<String> = MutableStateFlow("dummySerial")
    private var brandFlow: MutableStateFlow<String> = MutableStateFlow(Brands.SAMSUNG.name)

    override fun getSerial(): Flow<String> = serialFlow
    override suspend fun updateSerial(serial: String) = serialFlow.emit(serial)
    override fun getBrand(): Flow<String> = brandFlow
    override suspend fun updateBrand(brand: String) = brandFlow.emit(brand)
}