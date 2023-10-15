import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import moe.tlaster.precompose.PreComposeWindow
import view.HomeScreen
import view.Nav
import view.RichKey
import java.awt.Dimension
import java.awt.Toolkit

@Composable
@Preview
fun App() {
  var selectedPage by remember { mutableStateOf(1) }
  var selectedPageText by remember { mutableStateOf("") }

  Card(
    modifier = Modifier.fillMaxSize(),
    shape = RoundedCornerShape(10.dp),
    elevation = 0.dp,
    backgroundColor = Color.White
  ) {

    Row(modifier = Modifier.fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
        mutableListOf("產生 Rich standalone key", "456").forEachIndexed { index, it ->
          Button(modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(1.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
              println(it)
              selectedPage = index
              selectedPageText = it
            }) {
            Text(it)
          }
        }
      }

      Column(modifier = Modifier.fillMaxWidth().weight(0.7f)) {
        when (selectedPage) {
          1 -> {
            RichKey()
          }

          2 -> {
            HomeScreen()
          }

          3 -> {
            // 顯示第三個按鈕選擇的內容
          }
        }
      }

    }

  }

}

fun main() = application {

  PreComposeWindow(
    title = "Gateweb-Tool",
    onCloseRequest = ::exitApplication,
    state = WindowState(
      position = WindowPosition.Aligned(Alignment.Center),
      size = getPreferredWindowSize(1000, 1000)
    ),
    icon = painterResource("image/gateweb_logo.png")
  ) {

    MenuBar {
      Menu("Rich", mnemonic = 'F') {
        Item(
          "產 standalone key",
          onClick = {
            println("xxxx")

          },
          shortcut = KeyShortcut(Key.S, ctrl = true, shift = true)
        )
      }
    }
    Tray(
      icon = painterResource("image/gateweb_logo.png"),
      menu = {
        Item("exit", onClick = ::exitApplication)
      }
    )
//    HomeScreen()
    App()
  }
}


fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
  val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
  val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
  val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
  val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
  val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
  return DpSize(width.dp, height.dp)
}
