package com.serial.decoder.feature_onboarding.ui

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.core.ui.MainActivity
import com.serial.decoder.feature_onboarding.ui.OnBoardingScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import org.junit.Rule
import org.junit.Test

import com.serial.decoder.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OnBoardingScreenOneTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun loadOnBoardingScreen() {
        composeTestRule.activity.setContent {
            SerialDecoderTheme {
                OnBoardingScreen(windowWidthSizeClass = WindowWidthSizeClass.Compact, OnLaunchedBefore = {})
            }
        }

    }

    @Test
    fun shouldHaveImageOne_WhenOnBoardingScreenOneIsVisibleAtCompact() {
        // GIVEN our compose layout & imageContentDescription
        val imageContentDescription =
            composeTestRule.activity.getString(R.string.onboarding_select_brand_image)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithContentDescription(imageContentDescription).assertIsDisplayed()
    }

    @Test
    fun shouldHaveTitleOne_WhenOnBoardingScreenOneIsVisibleAtCompact() {
        // GIVEN our compose layout & selectABrandText
        val selectABrandText =
            composeTestRule.activity.getString(R.string.select_a_brand)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(selectABrandText).assertIsDisplayed()
    }

    @Test
    fun shouldHaveSubTitleOne_WhenOnBoardingScreenOneIsVisibleAtCompact() {
        // GIVEN our compose layout & subtitleMessageOne
        val subtitleMessageOne =
            composeTestRule.activity.getString(R.string.onboarding_message_one)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(subtitleMessageOne).assertIsDisplayed()
    }




}