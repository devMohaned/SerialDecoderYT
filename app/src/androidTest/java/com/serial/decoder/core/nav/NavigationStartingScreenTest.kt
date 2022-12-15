package com.serial.decoder.core.nav

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.MainActivityTest
import com.serial.decoder.R
import com.serial.decoder.core.assertCurrentRouteName
import com.serial.decoder.core.ui.MainViewModel
import com.serial.decoder.core.ui.SerialDecoderApp
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManagement
import com.serial.decoder.feature_onboarding.ui.OnBoardingScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import javax.inject.Inject


@HiltAndroidTest
class NavigationStartingScreenTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivityTest>()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManagement

    private lateinit var navController: TestNavHostController


    @Before()
    fun setupAppNavHost() {
        hiltTestRule.inject()
        sharedPreferenceManager.editor.clear().apply()
    }

    @Test
    fun shouldHaveOnBoardingScreen_WhenUserLaunchAppFirstTime() {
        givenTheScreen()
        navController.assertCurrentRouteName(NavigationGraph.OnBoarding.name)
    }

    @Test
    fun shouldNavigateToHome_WhenUserLaunchedAppBefore() {
        sharedPreferenceManager.updateUserLaunchFirstTime(isFirstTime = false)
        givenTheScreen()
        navController.assertCurrentRouteName(NavigationGraph.Home.name)
    }

    private fun givenTheScreen() {
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




}