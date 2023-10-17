package view

import androidx.compose.runtime.Composable
import view.page.Home
import view.page.RichKeyScreen
import view.page.TestA

const val RichStandaloneKey = "Rich standalone key"
const val Test = "Test"
val buttonList = listOf(
  RichStandaloneKey,
  Test,
  "TTTT",
)

@Composable
fun PageRouter(isHome: Boolean, selectedPage: String) {
  if (isHome)
    Home()
  else when (selectedPage) {
    RichStandaloneKey -> RichKeyScreen()
    Test -> TestA()
  }
}
