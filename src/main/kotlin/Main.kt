import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
  var text by remember { mutableStateOf("Hello, World!") }

  MaterialTheme {
    Button(onClick = {
      text = "Hello, Desktop!"
    }) {
      Text(text)
    }
  }
}

fun main() = application {
  Window(title = "Gateweb-Tool", onCloseRequest = ::exitApplication) {
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
    App()
  }
}
