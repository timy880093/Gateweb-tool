package view.page

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestA() {
  Button(modifier = Modifier, onClick = {}) { Text("material3") }
  Button(modifier = Modifier, onClick = {}) { Text("material") }
}