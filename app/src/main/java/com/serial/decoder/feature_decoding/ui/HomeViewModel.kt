package com.serial.decoder.feature_decoding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.VisibleForTesting
import com.serial.decoder.R
import com.serial.decoder.core.util.UIText
import com.serial.decoder.feature_decoding.data.local.entity.ManufactureEntity
import com.serial.decoder.feature_decoding.data.local.source.DataStoreManagement
import com.serial.decoder.feature_decoding.data.local.source.SerialDecoderDataStore
import com.serial.decoder.feature_decoding.data.local.util.Brands
import com.serial.decoder.feature_decoding.data.local.util.DecodingUtils.decodeSerial
import com.serial.decoder.feature_decoding.data.local.util.DecodingUtils.isCorrectSerial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUIEvent {
    data class ShowSnackBar(val text: String) : HomeUIEvent()
    object ShowBrandsDialog : HomeUIEvent()
}

data class HomeUIState(
    val isLoading: Boolean,
    val serial: String,
    val brandTitle: String,
    val manufacture: ManufactureEntity
)

const val SERIAL_INDEX = 0
const val BRAND_INDEX = 1

@HiltViewModel
class HomeViewModel @Inject constructor(private val serialDecoderDataStore: DataStoreManagement) :
    ViewModel() {

    val brands: Array<Brands> = Brands.values()

    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent: SharedFlow<HomeUIEvent> = _uiEvent.asSharedFlow()

    private val _uiState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(
            HomeUIState(
                isLoading = true,
                "",
                "",
                getManufactorBySerial("", Brands.SAMSUNG)
            )
        )
    val uiState: StateFlow<HomeUIState> = _uiState

    private val serialFlow: Flow<String> = serialDecoderDataStore.getSerial()
    private val brandFlow: Flow<String> = serialDecoderDataStore.getBrand()


    val serialWithBrand = serialFlow.combine(brandFlow) { serial: String, brand: String ->
        listOf(serial, brand)
    }.onStart {
        _uiState.value = _uiState.value.copy(isLoading = false)
    }.onEach { serialAndBrandList ->
        _uiState.value = _uiState.value.copy(
            manufacture = getManufactorBySerial(
                serialAndBrandList[SERIAL_INDEX],
                Brands.valueOf(serialAndBrandList[BRAND_INDEX])
            )
        )
    }


    fun updateSerial(serial: String) = viewModelScope.launch(Dispatchers.IO) {
        serialDecoderDataStore.updateSerial(serial)
    }

    fun updateBrand(brand: Brands) = viewModelScope.launch(Dispatchers.IO) {
        serialDecoderDataStore.updateBrand(brand.name)
    }

    private fun getManufactorBySerial(serial: String, brand: Brands): ManufactureEntity {
        if (isCorrectSerial(serial, brand)) return decodeSerial(serial, brand)

        return ManufactureEntity(
            UIText.StringResource(resId = R.string.couldnot_identify_type),
            UIText.StringResource(resId = R.string.couldnot_identify_country),
            UIText.StringResource(resId = R.string.couldnot_identify_date),
        )
    }

    fun showDialog() = viewModelScope.launch {
        _uiEvent.emit(HomeUIEvent.ShowBrandsDialog)
    }

    fun showSnackBar(text: String) = viewModelScope.launch {
        _uiEvent.emit(HomeUIEvent.ShowSnackBar(text))
    }
}