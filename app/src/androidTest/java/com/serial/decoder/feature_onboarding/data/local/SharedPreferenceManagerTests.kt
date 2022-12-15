package com.serial.decoder.feature_onboarding.data.local

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class SharedPreferenceManagerTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManagement

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun finish() {
        sharedPreferenceManager.editor.clear().apply()
    }


    @Test
    fun shouldReturnEditorWhenAccessedFromSharedPreferenceManager() {
        assert(sharedPreferenceManager.editor is SharedPreferences.Editor)
    }

    @Test
    fun shouldReturnTrue_WhenAppIsLaunchedFirstTime() {
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == true)
    }

    @Test
    fun shouldReturnFalse_WhenAppIsLaunchedAfterGettingStarted() {
        sharedPreferenceManager.updateUserLaunchFirstTime()
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == false)
    }



    @Test
    fun shouldReturnTrue_WhenNeverUpdatedUserFirstTime() {
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == true)
    }

    @Test
    fun shouldReturnTrue_WhenRelaunchedWithoutPressOnGetStarted() {
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == true)
    }

    @Test
    fun shouldReturnTrue_WhenUpdatedUserFirstTimeToTrue() {
        sharedPreferenceManager.updateUserLaunchFirstTime(isFirstTime = true)
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == true)
    }




    @Test
    fun shouldReturnFalse_WhenUpdatedUserFirstTimeToFalse() {
        sharedPreferenceManager.updateUserLaunchFirstTime(isFirstTime = false)
        val result = sharedPreferenceManager.getIsUserLaunchFirstTime()
        assert(result == false)
    }
}