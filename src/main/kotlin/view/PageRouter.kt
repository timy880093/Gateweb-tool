package view

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import view.page.Home
import view.page.RichKeyScreen
import view.page.TestA
import viewModel.RichKeyViewModel

const val RichStandaloneKey = "Rich standalone key"
const val Test = "Test"
val buttonList = listOf(
  RichStandaloneKey,
  Test,
  "TTTT",
)

@Composable
fun PageRouter(isHome: Boolean, selectedPage: String) {
  val richKeyViewModel = koinInject<RichKeyViewModel>()

  if (isHome)
    Home()
  else when (selectedPage) {
    RichStandaloneKey -> RichKeyScreen(richKeyViewModel)
    Test -> TestA()
  }
}
