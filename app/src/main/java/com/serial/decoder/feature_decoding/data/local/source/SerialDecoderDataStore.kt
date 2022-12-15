package com.serial.decoder.feature_decoding.data.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.serial.decoder.feature_decoding.data.local.util.Brands
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SerialDecoderDataStore @Inject constructor(val dataStore: DataStore<Preferences>) :
    DataStoreManagement {

    companion object {
        private const val SERIAL_KEY = "serial"
        private const val BRANDS_KEY = "brand"
        val serialKey = stringPreferencesKey(SERIAL_KEY)
        val brandKey = stringPreferencesKey(BRANDS_KEY)
    }

    override fun getSerial(): Flow<String> {
        val serialFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[serialKey] ?: ""
            }
        return serialFlow
    }

    override suspend fun updateSerial(serial: String) {
        dataStore.edit { dataStore ->
            dataStore[serialKey] = serial
        }
    }

    override fun getBrand(): Flow<String> {
        val brandFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[brandKey] ?: Brands.SAMSUNG.name
            }
        return brandFlow
    }

    override  suspend fun updateBrand(brand: String) {
        dataStore.edit { dataStore ->
            dataStore[brandKey] = brand
        }
    }

}