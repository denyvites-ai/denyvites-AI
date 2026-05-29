package com.example

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.ui.RehabViewModel
import com.example.data.RehabRepository
import com.example.data.AppDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SchoolShield", appName)
  }

  @Test
  fun `render main app without crashing`() {
    composeTestRule.setContent {
      val context = ApplicationProvider.getApplicationContext<Context>()
      val db = androidx.room.Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
      val repo = RehabRepository(db.profileDao(), db.habitDao(), db.workoutDao(), db.moodDao())
      val viewModel = RehabViewModel(repo)
      com.example.ui.RehabApp(viewModel = viewModel)
    }

    // Attempt to click Hub to see if anything crashes
    composeTestRule.onNodeWithText("Hub").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("The Novel").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("Action Taskforce").performClick()
    composeTestRule.waitForIdle()
    
    composeTestRule.onNodeWithText("Teacher Tools").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("Helplines").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("Quiz Center").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("Advice Bot").performClick()
    composeTestRule.waitForIdle()

    composeTestRule.onNodeWithText("Calendar").performClick()
    composeTestRule.waitForIdle()
  }
}

