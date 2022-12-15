package com.serial.decoder.feature_decoding.ui

import android.util.Log
import com.serial.decoder.core.util.UIText
import com.serial.decoder.feature_decoding.data.local.source.FakeSerialDecoderDataStore
import com.serial.decoder.feature_decoding.data.local.util.Brands
import com.serial.decoder.feature_onboarding.ui.OnBoardingUIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        homeViewModel = HomeViewModel(FakeSerialDecoderDataStore())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldStateIsLoading_WhenViewModelIsInitializedFirstTime() {
        val isLoading = homeViewModel.uiState.value.isLoading
        assert(isLoading == true)
    }

    @Test
    fun shouldEmitSnackBarEvent_WhenBarcodeScannedFailed() = runTest {
        homeViewModel.showSnackBar("Show_Snack_Bar_msg")
        val event = homeViewModel.uiEvent.first()
        assert(event is HomeUIEvent.ShowSnackBar)
    }


    @Test
    fun shouldEmitDialogEvent_WhenActionButtonPressed() = runTest {
        homeViewModel.showDialog()
        val event = homeViewModel.uiEvent.first()
        assert(event is HomeUIEvent.ShowBrandsDialog)
    }

    @Test
    fun shouldHaveSerial_WhenViewModelIsInitialized() = runTest {
        val serialWithBrand = homeViewModel.serialWithBrand.first()
        assert(serialWithBrand.get(0) == "dummySerial")
    }

    @Test
    fun shouldHaveBrand_WhenViewModelIsInitialized() = runTest {
        val serialWithBrand = homeViewModel.serialWithBrand.first()
        assert(serialWithBrand.get(1) == "SAMSUNG")
    }

    @Test
    fun shouldHaveSerialAndBrand_WhenViewModelIsInitialized() = runTest {
        val serialWithBrand = homeViewModel.serialWithBrand.first()
        assert(serialWithBrand.get(0) == "dummySerial")
        assert(serialWithBrand.get(1) == "SAMSUNG")
    }

    @Test
    fun shouldStateIsLoadingFalseAndSerialIsValidAndBrandIsValid_WhenSerialFlowIsChanged() =
        runTest {
            assert(homeViewModel.uiState.value.isLoading == true) // Initialized
            homeViewModel.updateSerial("OverriddenSerial")
            val values = mutableListOf<List<String>>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                homeViewModel.serialWithBrand.toList(values)
            }
            assert(values[0][0] == "OverriddenSerial")
            assert(values[0][1] == "SAMSUNG")
            assert(homeViewModel.uiState.value.isLoading == false)
            collectJob.cancel()

        }

    @Test
    fun shouldStateIsLoadingFalseAndSerialIsValid_WhenSerialFlowIsChanged() = runTest {
        assert(homeViewModel.uiState.value.isLoading == true) // Initialized
        homeViewModel.updateSerial("OverriddenSerial")
        val values = mutableListOf<List<String>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.serialWithBrand.toList(values)
        }
        assert(values[0][0] == "OverriddenSerial")
        assert(homeViewModel.uiState.value.isLoading == false)
        collectJob.cancel()

    }

    @Test
    fun shouldStateIsLoadingFalse_WhenSerialFlowIsChanged() = runTest {
        assert(homeViewModel.uiState.value.isLoading == true) // Initialized
        homeViewModel.updateSerial("OverriddenSerial")
        val values = mutableListOf<List<String>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.serialWithBrand.toList(values)
        }
        assert(homeViewModel.uiState.value.isLoading == false)
        collectJob.cancel()

    }

    @Test
    fun shouldStateIsLoadingFalseAndSerialIsValidAndBrandIsValid_WhenBrandFlowIsChanged() =
        runTest {
            assert(homeViewModel.uiState.value.isLoading == true) // Initialized
            homeViewModel.updateBrand(Brands.LG)
            val values = mutableListOf<List<String>>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                homeViewModel.serialWithBrand.toList(values)
            }
            assert(values[0][0] != null)
            assert(values[0][1] == "LG")
            assert(homeViewModel.uiState.value.isLoading == false)
            collectJob.cancel()

        }

    @Test
    fun shouldStateIsLoadingFalseAndSerialIsValid_WhenBrandFlowIsChanged() = runTest {
        assert(homeViewModel.uiState.value.isLoading == true) // Initialized
        homeViewModel.updateBrand(Brands.LG)
        val values = mutableListOf<List<String>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.serialWithBrand.toList(values)
        }
        assert(values[0][1] != null)
        assert(homeViewModel.uiState.value.isLoading == false)
        collectJob.cancel()

    }

    @Test
    fun shouldStateIsLoadingFalse_WhenBrandFlowIsChanged() = runTest {
        assert(homeViewModel.uiState.value.isLoading == true) // Initialized
        homeViewModel.updateBrand(Brands.LG)
        val values = mutableListOf<List<String>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.serialWithBrand.toList(values)
        }
        assert(homeViewModel.uiState.value.isLoading == false)
        collectJob.cancel()

    }

    @Test
    fun shouldStateIsLoadingFalseAndSerialIsValidAndBrandIsValid_WhenSerialFlowAndBrandIsChanged() =
        runTest {
            assert(homeViewModel.uiState.value.isLoading == true) // Initialized
            homeViewModel.updateSerial("OverriddenSerial")
            homeViewModel.updateBrand(Brands.LG)
            val values = mutableListOf<List<String>>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                homeViewModel.serialWithBrand.toList(values)
            }


            assert(values[0][0] == "OverriddenSerial")
            assert(values[0][1] == "LG")
            assert(homeViewModel.uiState.value.isLoading == false)


            collectJob.cancel()


        }


/*
    If you want to test the private function of getManufactorBySerial,
     however we do not care about private functions

    @Test
    fun shouldGetCorrectManufactorEntity_WhenSerialAndBrandAreProvidedCorrectly() {
        val serial = "07953NEM600767H"
        val brand = Brands.SAMSUNG
        val entity = homeViewModel.getManufactorBySerial(serial, brand)

        assert(entity.country is UIText.StringResource)
        assert(entity.date is UIText.DynamicString)
        assert(entity.type is UIText.StringResource)
    }

    @Test
    fun shouldGetInvalidManufactorEntity_WhenSerialAndBrandAreInvalid() {
        val serial = "wrongSerial"
        val brand = Brands.SAMSUNG
        val entity = homeViewModel.getManufactorBySerial(serial, brand)

        assert(entity.country is UIText.StringResource)
        assert(entity.date is UIText.StringResource)
        assert(entity.type is UIText.StringResource)
    }*/
}