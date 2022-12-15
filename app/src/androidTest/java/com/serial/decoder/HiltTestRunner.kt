package com.serial.decoder

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.serial.decoder.core.SerialDecoderApp
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}