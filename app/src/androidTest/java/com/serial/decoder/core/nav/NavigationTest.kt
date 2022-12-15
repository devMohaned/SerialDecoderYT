package com.serial.decoder.core.nav

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.serial.decoder.MainActivityTest
import com.serial.decoder.R
import com.serial.decoder.core.assertCurrentRouteName
import com.serial.decoder.core.swipeHorizontalToTheEnd
import com.serial.decoder.core.ui.SerialDecoderApp
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManagement
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivityTest>()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManagement

    private lateinit var navController: TestNavHostController


    @Before
    fun setupAppNavHost() {
        hiltTestRule.inject()
        sharedPreferenceManager.editor.clear().apply()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            SerialDecoderApp(
                windowSize = WindowWidthSizeClass.Compact,
                navController = navController
            )

        }
    }


    @Test
    fun shouldHaveOnBoardingScreen_WhenUserLaunchAppFirstTime() {
        navController.assertCurrentRouteName(NavigationGraph.OnBoarding.name)
    }

    @Test
    fun shouldNavigateToHome_WhenUserPressedGetStarted() {
        swipeToThirdTab()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.get_started))
            .performClick()

        navController.assertCurrentRouteName(NavigationGraph.Home.name)
    }

    @Test
    fun shouldNavigateToFAQ_WhenUserPressedFAQActionButton() {
        swipeToThirdTab()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.get_started))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.faq_button))
            .performClick()
        navController.assertCurrentRouteName(NavigationGraph.FAQ.name)
    }

    @Test
    fun shouldHaveBackButtonInFAQScreen_WhenUserPressedFAQActionButton() {
        swipeToThirdTab()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.get_started))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.faq_button))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_button))
            .assertIsDisplayed()
    }

    @Test
    fun shouldNavigateUpToHome_WhenUserPressedBackButton() {
        swipeToThirdTab()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.get_started))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.faq_button))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back_button))
            .performClick()
        navController.assertCurrentRouteName(NavigationGraph.Home.name)
    }

    private fun swipeToThirdTab() {
        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.onboarding_select_brand_image))
            .swipeHorizontalToTheEnd()

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity
                    .getString(R.string.onboarding_insert_serial_number_image)
            )
            .swipeHorizontalToTheEnd()
    }

}