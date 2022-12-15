package com.serial.decoder.feature_onboarding.ui

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.ui.theme.SerialDecoderTheme
import org.junit.Rule
import org.junit.Test

import com.serial.decoder.R
import com.serial.decoder.core.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OnBoardingScreenTwoTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun loadOnBoardingScreen() {
        composeTestRule.activity.setContent {
            SerialDecoderTheme {
                OnBoardingScreen(
                    windowWidthSizeClass = WindowWidthSizeClass.Compact,
                    OnLaunchedBefore = {})
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.onboarding_select_brand_image))
            .performTouchInput { swipe(Offset(600f, 0f), Offset(0f, 0f)) }
    }

    @Test
    fun shouldHaveImageTwo_WhenSwipingToOnBoardingScreenTwoIsVisibleAtCompact() {
        // GIVEN our compose layout & second image content
        val imageContentDescription =
            composeTestRule.activity.getString(R.string.onboarding_insert_serial_number_image)
        // WHEN swiping from Start to End (1 Time)
        //THEN first dot should be orange
        composeTestRule.onNodeWithContentDescription(imageContentDescription).assertIsDisplayed()
    }

    @Test
    fun shouldHaveTitleTwo_WhenSwipingToOnBoardingScreenTwoIsVisibleAtCompact() {
        // GIVEN our compose layout & selectABrandText
        val insertSerialNumber =
            composeTestRule.activity.getString(R.string.insert_serial_number)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(insertSerialNumber).assertIsDisplayed()
    }

    @Test
    fun shouldHaveSubTitleTwo_WhenSwipingOnBoardingScreenTwoIsVisibleAtCompact() {
        // GIVEN our compose layout & subtitleMessageOne
        val subtitleMessageTwo =
            composeTestRule.activity.getString(R.string.onboarding_message_two)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(subtitleMessageTwo).assertIsDisplayed()
    }


}