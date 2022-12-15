package com.serial.decoder.feature_decoding

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.R
import com.serial.decoder.core.ui.MainActivity
import com.serial.decoder.feature_decoding.data.local.util.Brands
import com.serial.decoder.feature_decoding.ui.HomeScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeScreenTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun loadOnBoardingScreen() {
        composeTestRule.activity.setContent {
            SerialDecoderTheme {
                HomeScreen(onFAQActionPressed = {})
            }
        }

    }

    @Test
    fun ShouldContainAppBarIcon_WhenScreenIsOpened() {
        // Given content description of app icon
        val appIconContentDescription = composeTestRule.activity.getString(
            R.string.scanner_icon
        )
        // When home is open
        // then it should be visible at top bar
        composeTestRule.onNodeWithContentDescription(appIconContentDescription).assertIsDisplayed()

    }

    @Test
    fun ShouldContainAppBarTitle_WhenScreenIsOpened() {
        val appTitle = "AppTitle"
        composeTestRule.onNodeWithTag(appTitle).assertIsDisplayed()
    }

    @Test
    fun ShouldContainAppBarActionOneIcon_WhenScreenIsOpened() {
        val appIconContentDescription = composeTestRule.activity.getString(
            R.string.change_manufactor
        )
        composeTestRule.onNodeWithContentDescription(appIconContentDescription).assertIsDisplayed()
    }

    @Test
    fun ShouldContainAppBarActionTwoIcon_WhenScreenIsOpened() {
        val appIconContentDescription = composeTestRule.activity.getString(
            R.string.faq_button
        )
        composeTestRule.onNodeWithContentDescription(appIconContentDescription).assertIsDisplayed()
    }

    @Test
    fun ShouldContainAppBarActionOneAndTwoIcon_WhenScreenIsOpened() {
        val appIconContentDescriptionOne = composeTestRule.activity.getString(
            R.string.change_manufactor
        )
        val appIconContentDescriptionTwo = composeTestRule.activity.getString(
            R.string.faq_button
        )
        composeTestRule.onNodeWithContentDescription(appIconContentDescriptionOne)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(appIconContentDescriptionTwo)
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowDialog_WhenChangeManufacturIconButtonIsClicked() {
        val appIconContentDescription = composeTestRule.activity.getString(
            R.string.change_manufactor
        )
        composeTestRule.onNodeWithContentDescription(appIconContentDescription).performClick()
        composeTestRule.onNode(isDialog()).assertIsDisplayed()
    }


    @Test
    fun ShouldContainManufactorTypeIconInHomeContent_WhenScreenIsOpened() {
        val manufactorType = composeTestRule.activity.getString(
            R.string.manufactored_type_image
        )
        composeTestRule.onNodeWithContentDescription(manufactorType).assertIsDisplayed()
    }

    @Test
    fun ShouldContainManufactorLocationIconInHomeContent_WhenScreenIsOpened() {
        val manufactorLocation = composeTestRule.activity.getString(
            R.string.manufactored_location_image
        )
        composeTestRule.onNodeWithContentDescription(manufactorLocation).assertIsDisplayed()

    }

    @Test
    fun ShouldContainManufactorDateIconInHomeContent_WhenScreenIsOpened() {
        val manufactorDate = composeTestRule.activity.getString(
            R.string.manufactored_date_image
        )
        composeTestRule.onNodeWithContentDescription(manufactorDate).assertIsDisplayed()

    }

    @Test
    fun ShouldContainManufactorTypeAndLocationAndDateTextInHomeContent_WhenScreenIsOpened() {
        val manufactorType = composeTestRule.activity.getString(
            R.string.manufactored_type_image
        )
        val manufactorLocation = composeTestRule.activity.getString(
            R.string.manufactored_location_image
        )
        val manufactorDate = composeTestRule.activity.getString(
            R.string.manufactored_date_image
        )

        composeTestRule.onNodeWithContentDescription(manufactorType).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(manufactorLocation).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(manufactorDate).assertIsDisplayed()

    }

    @Test
    fun ShouldContainSerialTextFieldInHomeContent_WhenScreenIsOpened() {
        val label = composeTestRule.activity.getString(R.string.insert_serial_number)
        composeTestRule.onNodeWithText(label).assertIsDisplayed()
    }

    @Test
    fun ShouldContainBottomIconButtonNearRightBottomCornerInHomeContent_WhenScreenIsOpened() {
        val bottomSheetIconButtonContentDescription =
            composeTestRule.activity.getString(R.string.bottom_sheet_puller)
        composeTestRule.onNodeWithContentDescription(bottomSheetIconButtonContentDescription)
            .assertIsDisplayed()
    }

    @Test
    fun ShouldContainScanText_WhenBottomSheetIsExpanded() {
        val bottomSheetIconButtonContentDescription =
            composeTestRule.activity.getString(R.string.bottom_sheet_puller)

        val scanText = composeTestRule.activity.getString(R.string.scan_serial_with_qr)

        composeTestRule.onNodeWithContentDescription(bottomSheetIconButtonContentDescription)
            .performTouchInput {
                swipeUp(400f, 0f)
            }


        composeTestRule.onNodeWithText(scanText)
            .assertIsDisplayed()
        composeTestRule.onRoot().printToLog("currentLabelExists")

    }


    @Test
    fun ShouldContainCameraView_WhenBottomSheetIsExpanded() {
        val bottomSheetIconButtonContentDescription =
            composeTestRule.activity.getString(R.string.bottom_sheet_puller)


        composeTestRule.onNodeWithContentDescription(bottomSheetIconButtonContentDescription)
            .performTouchInput {
                swipeUp(400f, 0f)
            }


        composeTestRule.onNodeWithTag("CameraTag")
            .assertExists()
    }


}