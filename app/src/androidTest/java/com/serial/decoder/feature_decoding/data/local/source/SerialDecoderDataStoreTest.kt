package com.serial.decoder.feature_decoding.data.local.source

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.feature_decoding.data.local.util.Brands
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


private const val SERIAL_KEY = "serial"
private const val BRANDS_KEY = "brand"
val serialKey = stringPreferencesKey(SERIAL_KEY)
val brandKey = stringPreferencesKey(BRANDS_KEY)

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SerialDecoderDataStoreTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var dataStore: SerialDecoderDataStore

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        hiltRule.inject()
    }

    @After
    fun teardown() {
        runTest {
            dataStore.dataStore.edit { it.clear() }
        }
        Dispatchers.resetMain()
    }

    @Test
    fun shouldGetSerial_WhenDataStoreIsUpdatedWithSerial() = runTest {
        dataStore.updateSerial("SerialValue")
        val serial: String? = dataStore.dataStore.data.first().get(serialKey)
        assertTrue(serial == "SerialValue")
    }

    @Test
    fun shouldGetBrand_WhenDataStoreIsUpdatedWithBrand() = runTest {
        dataStore.updateBrand("BrandValue")
        val brand: String? = dataStore.dataStore.data.first().get(brandKey)
        assertTrue(brand == "BrandValue")
    }

    @Test
    fun shouldGetBrandAndSerial_WhenDataStoreIsUpdatedWithSerialAndBrand() = runTest {
        dataStore.updateSerial("SerialValue")
        dataStore.updateBrand("BrandValue")
        val serial: String? = dataStore.dataStore.data.first().get(serialKey)
        val brand: String? = dataStore.dataStore.data.first().get(brandKey)
        assertTrue(brand == "BrandValue" && serial == "SerialValue")
    }

    @Test
    fun shouldGetBrandAndSerialMethods_WhenDataStoreIssUpdatedWithSerialAndBrand() = runTest {
        dataStore.updateSerial("SerialValue")
        dataStore.updateBrand("BrandValue")
        val serial: String = dataStore.getSerial().first()
        val brand: String = dataStore.getBrand().first()
        assertTrue(brand == "BrandValue" && serial == "SerialValue")
    }

    @Test
    fun shouldGetBrandMethodsReturnFirstValueCorrectly_WhenDataStoreIssUpdatedWithBrand() =
        runTest {
            dataStore.updateBrand("BrandValue")
            val brand: String = dataStore.getBrand().first()
            assertTrue(brand == "BrandValue")
        }

    @Test
    fun shouldGetSerialMethod_WhenDataStoreIssUpdatedWithSerial() = runTest {
        dataStore.updateSerial("SerialValue")
        val serial: String = dataStore.getSerial().first()
        assertTrue(serial == "SerialValue")
    }
}