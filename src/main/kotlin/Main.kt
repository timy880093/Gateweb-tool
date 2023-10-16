import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeWindow
import view.RichKeyScreen
import view.TestA
import java.awt.Dimension
import java.awt.Toolkit

@Composable
@Preview
fun App() {
  val btnRichKey = "Rich standalone key"
  val btnTest = "Test"
  val buttonList = listOf(
    btnRichKey,
    btnTest,
  )

//  val map = mapOf(
//    "Rich standalone key" to RichKey(),
//    "Test" to TestA()
//  )

  var selectedPageText by remember { mutableStateOf("") }

  Card(
    modifier = Modifier.fillMaxSize().padding(10.dp),
    shape = RoundedCornerShape(10.dp),
    elevation = 0.dp,
    backgroundColor = Color.White
  ) {

    Row {
      Column(
        modifier = Modifier.fillMaxSize().weight(0.3f)
          .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
          .verticalScroll(rememberScrollState())
      ) {
        buttonList.forEach {
//        map.keys.forEach {
          TextButton(modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(1.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified),
            onClick = {
              println(it)
              selectedPageText = it
            }) {
            Text(
              text = it,
              style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
          }
          Spacer(modifier = Modifier.padding(2.dp))
        }
      }

      Column(
        modifier = Modifier.fillMaxWidth().weight(0.7f).padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        when (selectedPageText) {
          btnRichKey -> RichKeyScreen()
          btnTest -> TestA()
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

//    MenuBar {
//      Menu("Rich", mnemonic = 'F') {
//        Item(
//          "產 standalone key",
//          onClick = {
//            println("xxxx")
//
//          },
//          shortcut = KeyShortcut(Key.S, ctrl = true, shift = true)
//        )
//      }
//    }
    Tray(
      icon = painterResource("image/gateweb_logo.png"),
      menu = {
        Item("exit", onClick = ::exitApplication)
      }
    )
//    HomeScreen()
    MaterialTheme {
      Surface(color = Color.DarkGray) {
        App()
      }
    }
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
