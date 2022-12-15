package com.serial.decoder.feature_onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

data class OnBoardingUIState(val isLoading: Boolean = false)

sealed class OnBoardingUIEvent {
    object UpdateFirstLaunch : OnBoardingUIEvent()
}

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val sharedPreferenceManagement: SharedPreferenceManagement) :
    ViewModel() {

    private val _uiState = MutableStateFlow<OnBoardingUIState>(
        OnBoardingUIState(
            isLoading = false
        )
    )
    val uiState: StateFlow<OnBoardingUIState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<OnBoardingUIEvent>()
    val uiEvent: SharedFlow<OnBoardingUIEvent> = _uiEvent.asSharedFlow()

    fun setLoading(isLoading: Boolean) {
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    fun updateFirstLaunch(isFirstTime: Boolean = false) {
        setLoading(!isFirstTime)
        sharedPreferenceManagement.updateUserLaunchFirstTime(isFirstTime)
        setLoading(isFirstTime)
        viewModelScope.launch {
            _uiEvent.emit(OnBoardingUIEvent.UpdateFirstLaunch)
        }
    }
}