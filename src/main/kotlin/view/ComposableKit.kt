package view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.TextFieldState

@Composable
fun Space(int: Int) {
  Spacer(modifier = Modifier.padding(int.dp))
}

@Composable
fun ValidationTextField(
  label: String,
  map: Map<String, (String) -> Boolean>? = null,
  onStateChanged: (TextFieldState) -> Unit
) {
  var text by remember { mutableStateOf("") }
  var isError by remember { mutableStateOf(false) }

  TextField(
    modifier = Modifier.fillMaxWidth().height(50.dp),
    label = { Text(label) },
    value = text,
    onValueChange = {
      text = it
      map?.let { m -> isError = m.values.any { func -> !func(text) } }
      // call back 將組件內 state 傳出去
      onStateChanged(TextFieldState(text, isError))
    },
    isError = isError
  )

  map?.let { m ->
    m.filterValues { func -> !func(text) }.forEach { (t, _) ->
      Text(
        text = t,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }
  }

}