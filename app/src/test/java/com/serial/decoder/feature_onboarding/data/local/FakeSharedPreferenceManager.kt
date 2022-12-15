package com.serial.decoder.feature_onboarding.data.local

import android.content.SharedPreferences

class FakeSharedPreferenceManager : SharedPreferenceManagement {
    override val editor: SharedPreferences.Editor
        get() = null!! // Not Required

    var isFirstTime = true

    override fun getIsUserLaunchFirstTime(): Boolean {
        return isFirstTime
    }

    override fun updateUserLaunchFirstTime(isFirstTime: Boolean) {
        this.isFirstTime = isFirstTime
    }
}