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
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OnBoardingScreenExpandedTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Test
    fun ShouldHaveImageAndTitleAndSubTitleOfEachScreen_WhenOnBoardingScreenIsVisibleAtExpanded() {
        // Given our compose layout and their content description
        composeTestRule.activity.setContent {
            SerialDecoderTheme {
                OnBoardingScreen(windowWidthSizeClass = WindowWidthSizeClass.Expanded, OnLaunchedBefore = {})
            }
        }

        val imageContentDescription =
            composeTestRule.activity.getString(R.string.onboarding_select_brand_image)
        val selectABrandText =
            composeTestRule.activity.getString(R.string.select_a_brand)
        val subtitleMessageOne =
            composeTestRule.activity.getString(R.string.onboarding_message_one)

        val imageContentDescription2 =
            composeTestRule.activity.getString(R.string.onboarding_insert_serial_number_image)

        val insertSerialNumber =
            composeTestRule.activity.getString(R.string.insert_serial_number)

        val subtitleMessageTwo =
            composeTestRule.activity.getString(R.string.onboarding_message_two)

        val imageContentDescription3 =
            composeTestRule.activity.getString(R.string.onboarding_view_details_about_device_image)

        val viewAndRead =
            composeTestRule.activity.getString(R.string.view_and_read)

        val subtitleMessageThree =
            composeTestRule.activity.getString(R.string.onboarding_message_three)

        val getStartedButton =             composeTestRule.activity.getString(R.string.get_started)


        // WHEN launched in EXPANDED or with configure change
        // Then all composables will be visible
        composeTestRule.onNodeWithContentDescription(imageContentDescription).assertIsDisplayed()
        composeTestRule.onNodeWithText(selectABrandText).assertIsDisplayed()
        composeTestRule.onNodeWithText(subtitleMessageOne).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(imageContentDescription2).assertIsDisplayed()
        composeTestRule.onNodeWithText(insertSerialNumber).assertIsDisplayed()
        composeTestRule.onNodeWithText(subtitleMessageTwo).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(imageContentDescription3).assertIsDisplayed()
        composeTestRule.onNodeWithText(viewAndRead).assertIsDisplayed()
        composeTestRule.onNodeWithText(subtitleMessageThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(getStartedButton).assertExists()


    }


}