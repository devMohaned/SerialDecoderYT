package com.serial.decoder.feature_onboarding.ui

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.core.ui.MainActivity
import com.serial.decoder.feature_onboarding.ui.OnBoardingScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import org.junit.Rule
import org.junit.Test

import com.serial.decoder.R
import com.serial.decoder.core.swipeHorizontalToTheEnd
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OnBoardingScreenThreeTests {
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

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.onboarding_select_brand_image))
            .swipeHorizontalToTheEnd()

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.onboarding_insert_serial_number_image))
            .swipeHorizontalToTheEnd()
    }

    @Test
    fun shouldHaveImageThree_WhenSwipingToOnBoardingScreenThreeIsVisibleAtCompact() {
        // GIVEN our compose layout & second image content
        val imageContentDescription =
            composeTestRule.activity.getString(R.string.onboarding_view_details_about_device_image)
        // WHEN swiping from Start to End (1 Time)
        //THEN first dot should be orange
        composeTestRule.onNodeWithContentDescription(imageContentDescription).assertIsDisplayed()
    }

    @Test
    fun shouldHaveTitleThree_WhenSwipingToOnBoardingScreenThreeIsVisibleAtCompact() {
        // GIVEN our compose layout & selectABrandText
        val viewAndRead =
            composeTestRule.activity.getString(R.string.view_and_read)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(viewAndRead).assertIsDisplayed()
    }

    @Test
    fun shouldHaveSubTitleThree_WhenSwipingOnBoardingScreenThreeIsVisibleAtCompact() {
        // GIVEN our compose layout & subtitleMessageOne
        val subtitleMessageThree =
            composeTestRule.activity.getString(R.string.onboarding_message_three)
        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(subtitleMessageThree).assertIsDisplayed()
    }

    @Test
    fun shouldHaveGetStartedButton_WhenSwipingOnBoardingScreenThreeIsVisibleAtCompact() {
        // GIVEN our compose layout & subtitleMessageOne
        val getStartedButton =             composeTestRule.activity.getString(R.string.get_started)

        // WHEN launched
        //THEN image should be visible
        composeTestRule.onNodeWithText(getStartedButton).assertIsDisplayed()
    }



}