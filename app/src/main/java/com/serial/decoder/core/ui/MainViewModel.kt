package com.serial.decoder.core.ui

import androidx.lifecycle.ViewModel
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(sharedPreferenceManagement: SharedPreferenceManagement) :
    ViewModel() {
    val isFirstTime = sharedPreferenceManagement.getIsUserLaunchFirstTime()
}