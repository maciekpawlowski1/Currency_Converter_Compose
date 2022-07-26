package com.pawlowski.currencyconvertercompose

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class MainActivityUiTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun ratesAreDisplayed()
    {
        //TODO: Check does it always wait enough for result
        composeTestRule.onAllNodesWithTag("rate_tittle", true).onFirst().assertIsDisplayed()
    }

    @Test
    fun choosingRateDialogDisplays()
    {
        composeTestRule.onNodeWithTag("choose_currency_card").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithTag("dialog_box").assertExists().assertIsDisplayed()

    }

    @Test
    fun searchingInDialogWorksCorrectly()
    {
        composeTestRule.onNodeWithTag("choose_currency_card").assertExists().performClick()
        composeTestRule.onNodeWithTag("search_close_button").assertDoesNotExist()
        composeTestRule.onNodeWithTag("search_field").assertIsDisplayed().performTextInput("pl")
        composeTestRule.onNodeWithTag("search_close_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("PLN").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("search_field").performTextReplacement("ad")
        composeTestRule.onNode(hasText("CAD").and(hasTestTag("search_rate_tittle")), true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("search_close_button").performClick()
        composeTestRule.onNodeWithTag("search_close_button").assertDoesNotExist()
        composeTestRule.onNodeWithTag("search_field", true).assertTextEquals("")


    }
}