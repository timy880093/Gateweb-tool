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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun App() {
  var isDark by remember { mutableStateOf(true) }
  var isHome by remember { mutableStateOf(true) }
  var selectedPage by remember { mutableStateOf("") }

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
                shape = RoundedCornerShape(5.dp)
              )
              .verticalScroll(rememberScrollState())
          ) {
            buttonList.forEach {
//        map.keys.forEach {
              TextButton(modifier = Modifier.fillMaxWidth().height(40.dp),
                onClick = {
                  println(it)
                  selectedPage = it
                  isHome = false
                }) {
                Text(
                  text = it,
                  style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
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
            PageRouter(isHome, selectedPage)
          }

        }
      }

    }
  }

}