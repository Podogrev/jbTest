import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.example.project.MainActivity
import org.junit.Rule
import org.junit.Test

class LazyColumnTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLazyColumnRendersListWithItems() {
        composeTestRule.setContent {
            LazyColumn {
                items(5) { index ->
                    Text(text = "Item $index")
                }
            }
        }

        for (i in 0..4) {
            composeTestRule.onNodeWithText("Item $i").assertExists()
        }
    }

    @Test
    fun testLazyColumnScrolling() {
        composeTestRule.setContent {
            LazyColumn {
                items(100) { index ->
                    Text(text = "Item $index")
                }
            }
        }

        composeTestRule.onNodeWithText("Item 0").assertExists()
        composeTestRule.onNodeWithText("Item 99").performScrollTo().assertExists()
    }

    @Test
    fun testLazyColumnRendersEmptyList() {
        composeTestRule.setContent {
            LazyColumn {
                // Пустой список
            }
        }

        composeTestRule.onNodeWithText("Item").assertDoesNotExist()
    }

    @Test
    fun testLazyColumnItemClick() {
        var clickedIndex = -1

        composeTestRule.setContent {
            LazyColumn {
                items(5) { index ->
                    Text(text = "Item $index", modifier = Modifier.clickable { clickedIndex = index })
                }
            }
        }

        composeTestRule.onNodeWithText("Item 2").performClick()
        assert(clickedIndex == 2)
    }

    @Test
    fun testLazyColumnDynamicUpdate() {
        var items by mutableStateOf(listOf("Item 1", "Item 2"))

        composeTestRule.setContent {
            LazyColumn {
                items(items) { item ->
                    Text(text = item)
                }
            }
        }

        composeTestRule.onNodeWithText("Item 1").assertExists()
        composeTestRule.onNodeWithText("Item 2").assertExists()

        // Добавляем новый элемент
        items = items + "Item 3"
        composeTestRule.onNodeWithText("Item 3").assertExists()

        // Удаляем элемент
        items = items.filter { it != "Item 1" }
        composeTestRule.onNodeWithText("Item 1").assertDoesNotExist()
    }

    @Test
    fun testLazyColumnWithSingleItem() {
        composeTestRule.setContent {
            LazyColumn {
                item {
                    Text(text = "Item 1")
                }
            }
        }

        composeTestRule.onNodeWithText("Item 1").assertExists()
    }

    @Test
    fun testLazyColumnWithLargeList() {
        composeTestRule.setContent {
            LazyColumn {
                items(1000) { index ->
                    Text(text = "Item $index")
                }
            }
        }

        composeTestRule.onNodeWithText("Item 0").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 999").performScrollTo().assertExists().assertIsDisplayed()
    }

    @Test
    fun testLazyColumnWithTallItems() {
        composeTestRule.setContent {
            LazyColumn {
                items(5) { index ->
                    Text(text = "Tall Item $index", modifier = Modifier.height(200.dp))
                }
            }
        }

        for (i in 0..4) {
            composeTestRule.onNodeWithText("Tall Item $i").assertExists()
        }
    }

    @Test
    fun testLazyColumnOrientationChange() {
        composeTestRule.setContent {
            LazyColumn {
                items(5) { index ->
                    Text(text = "Item $index")
                }
            }
        }

        for (i in 0..4) {
            composeTestRule.onNodeWithText("Item $i").assertExists()
        }

        changeOrientation(activityRule.scenario, false)

        for (i in 0..4) {
            composeTestRule.onNodeWithText("Item $i").assertExists()
        }

        changeOrientation(activityRule.scenario, true)

        for (i in 0..4) {
            composeTestRule.onNodeWithText("Item $i").assertExists()
        }
    }

    private fun changeOrientation(scenario: ActivityScenario<*>, isPortrait: Boolean) {
        scenario.onActivity { activity ->
            val orientation = if (isPortrait) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            activity.requestedOrientation = orientation
        }
    }
}

