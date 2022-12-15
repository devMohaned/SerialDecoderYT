package com.serial.decoder.feature_faq

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serial.decoder.R
import com.serial.decoder.core.ui.MainActivity
import com.serial.decoder.feature_faq.ui.FrequentlyAskedQuestionsScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FAQScreenTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun loadOnBoardingScreen() {
        composeTestRule.activity.setContent {
            SerialDecoderTheme {
                FrequentlyAskedQuestionsScreen(onBackButtonPressed = {})
            }
        }

    }

    @Test
    fun shouldSeeThreeQuestions_WhenFAQScreenIsOpened() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        // When launched
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
    }

    @Test
    fun shouldSeeThreeQuestionsAndAnswerOne_WhenFAQScreenQuestionOneIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerOne = composeTestRule.activity.getString(R.string.ans_1)
        // When pressed a question one
        composeTestRule.onNodeWithText(questionOne).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerOne).assertIsDisplayed()
    }


    @Test
    fun shouldSeeThreeQuestionsAndAnswerTwo_WhenFAQScreenQuestionTwoIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerTwo = composeTestRule.activity.getString(R.string.ans_2)
        // When pressed a question two
        composeTestRule.onNodeWithText(questionTwo).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerTwo).assertIsDisplayed()
    }

    @Test
    fun shouldSeeThreeQuestionsAndAnswerThree_WhenFAQScreenQuestionThreeIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerThree = composeTestRule.activity.getString(R.string.ans_3)
        // When pressed a question three
        composeTestRule.onNodeWithText(questionThree).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerThree).assertIsDisplayed()
    }


    @Test
    fun shouldSeeThreeQuestionsAndAnswerOneAndTwo_WhenFAQScreenQuestionOneAndTwoIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerOne = composeTestRule.activity.getString(R.string.ans_1)
        val answerTwo = composeTestRule.activity.getString(R.string.ans_2)
        // When pressed a question three
        composeTestRule.onNodeWithText(questionOne).performClick()
        composeTestRule.onNodeWithText(questionTwo).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerTwo).assertIsDisplayed()
    }

    @Test
    fun shouldSeeThreeQuestionsAndAnswerOneAndThree_WhenFAQScreenQuestionOneAndThreeIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerOne = composeTestRule.activity.getString(R.string.ans_1)
        val answerThree = composeTestRule.activity.getString(R.string.ans_3)
        // When pressed a question three
        composeTestRule.onNodeWithText(questionOne).performClick()
        composeTestRule.onNodeWithText(questionThree).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerThree).assertIsDisplayed()
    }

    @Test
    fun shouldSeeThreeQuestionsAndAnswerTwoAndThree_WhenFAQScreenQuestionTwoAndThreeIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerTwo = composeTestRule.activity.getString(R.string.ans_2)
        val answerThree = composeTestRule.activity.getString(R.string.ans_3)
        // When pressed a question three
        composeTestRule.onNodeWithText(questionTwo).performClick()
        composeTestRule.onNodeWithText(questionThree).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerThree).assertIsDisplayed()
    }


    @Test
    fun shouldSeeThreeQuestionsAndAnswerOneAndTwoAndThree_WhenFAQScreenQuestionOneAndTwoAndThreeIsPressed() {
        // Given The compose layout composables
        val questionOne = composeTestRule.activity.getString(R.string.faq_1)
        val questionTwo = composeTestRule.activity.getString(R.string.faq_2)
        val questionThree = composeTestRule.activity.getString(R.string.faq_3)
        val answerOne = composeTestRule.activity.getString(R.string.ans_1)
        val answerTwo = composeTestRule.activity.getString(R.string.ans_2)
        val answerThree = composeTestRule.activity.getString(R.string.ans_3)
        // When pressed a question three
        composeTestRule.onNodeWithText(questionOne).performClick()
        composeTestRule.onNodeWithText(questionTwo).performClick()
        composeTestRule.onNodeWithText(questionThree).performClick()
        // Then check if 3 questions are visible
        composeTestRule.onNodeWithText(questionOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(questionThree).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerOne).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerTwo).assertIsDisplayed()
        composeTestRule.onNodeWithText(answerThree).assertIsDisplayed()
    }

    @Test
    fun shouldSeeContactDeveloperButton_WhenFAQScreenIsVisible() {
        // Given The compose layout composables
        val contactDeveloper = composeTestRule.activity.getString(R.string.contact_developer)
        // When showed
        // Then check if those developer button is visible
        composeTestRule.onNodeWithText(contactDeveloper).assertIsDisplayed()
    }
    @Test
    fun shouldSeeRateAppButton_WhenFAQScreenIsVisible() {
        // Given The compose layout composables
        val rateApp = composeTestRule.activity.getString(R.string.rate_the_app)
        // When showed
        // Then check if rate app button is visible
        composeTestRule.onNodeWithText(rateApp).assertIsDisplayed()
    }

    @Test
    fun shouldSeeTwoButtons_WhenFAQScreenIsVisible() {
        // Given The compose layout composables
        val contactDeveloper = composeTestRule.activity.getString(R.string.contact_developer)
        val rateApp = composeTestRule.activity.getString(R.string.rate_the_app)
        // When showed
        // Then check if those 2 buttons are visible
        composeTestRule.onNodeWithText(contactDeveloper).assertIsDisplayed()
        composeTestRule.onNodeWithText(rateApp).assertIsDisplayed()
    }


}