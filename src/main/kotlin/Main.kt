import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import org.koin.compose.KoinApplication
import view.App
import java.awt.Dimension
import java.awt.Toolkit

fun main() = application {

  Window(
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
    KoinApplication(application = { modules(services) }) {
      App()
    }
  }
}


private fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
  val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
  val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
  val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
  val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
  val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
  return DpSize(width.dp, height.dp)
}
