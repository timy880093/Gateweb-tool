package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import view.page.Home
import view.page.RichKeyScreen
import view.page.TestA

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
  var isDark by remember { mutableStateOf(true) }
  var isHome by remember { mutableStateOf(true) }
  var selectedPageText by remember { mutableStateOf("") }

//  Card(
//    modifier = Modifier.fillMaxSize().padding(10.dp),
//    shape = RoundedCornerShape(10.dp),
//    elevation = 0.dp,
//    backgroundColor = Color.White
//  ) {
  DarkModeTheme(isDark = isDark) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.TopEnd
    ) {
      Column(modifier = Modifier) {
        // Top Navigation Bar
        Row(
          modifier = Modifier.fillMaxWidth().padding(5.dp),
          horizontalArrangement = Arrangement.End
        ) {
          // 首頁
          IconButton(onClick = ({ isHome = true })) {
            Icon(
              Icons.Outlined.Home,
              contentDescription = "Home",
              modifier = Modifier.size(48.dp)
            )
          }
          // 切換黑暗模式 Icon
          IconButton(onClick = ({ isDark = !isDark })) {
            Icon(
              if (isDark) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
              contentDescription = "Theme",
              modifier = Modifier.size(48.dp)
            )
          }
        }

        // Main
        Row {
          // Left Bar
          Column(
            modifier = Modifier.fillMaxSize().weight(0.3f)
              .background(
                color = if (isDark) Color.DarkGray else Color.LightGray,
                shape = RoundedCornerShape(10.dp)
              )
              .verticalScroll(rememberScrollState())
          ) {
            buttonList.forEach {
//        map.keys.forEach {
              TextButton(modifier = Modifier.fillMaxWidth().height(40.dp),
                shape = RoundedCornerShape(1.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Unspecified),
                onClick = {
                  println(it)
                  selectedPageText = it
                  isHome = false
                }) {
                Text(
                  text = it,
                  style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color.White else Color.Black
                  )
                )
              }
              Space(2)
            }
          }
          // Right Content
          Column(
            modifier = Modifier.fillMaxWidth().weight(0.7f).padding(20.dp)
              .verticalScroll(rememberScrollState())
          ) {
            if (isHome)
              Home()
            else when (selectedPageText) {
              btnRichKey -> RichKeyScreen()
              btnTest -> TestA()
            }

          }

        }
      }

    }
  }

}