package com.serial.decoder.feature_onboarding.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(val sharedPreferences: SharedPreferences) :
    SharedPreferenceManagement {

    companion object {
        const val IS_FIRST_TIME = "is_first_time"
        const val PREF = "SerialDecodePrefName"

    }

    override val editor: SharedPreferences.Editor
        get() = sharedPreferences.edit()

    override fun getIsUserLaunchFirstTime(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true)
    }

    override fun updateUserLaunchFirstTime(isFirstTime: Boolean) {
        with(editor) {
            putBoolean(IS_FIRST_TIME, isFirstTime)
            apply()
        }
    }


}