package view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
  defaultValue:String,
  enableValidate: Boolean,
  validators: Map<String, (String) -> Boolean>,
  onStateChanged: (TextFieldState) -> Unit
) {
  var value by remember { mutableStateOf(defaultValue) }
  var isError by remember { mutableStateOf(false) }

  OutlinedTextField(
    modifier = Modifier.fillMaxWidth(),
    label = { Text(label) },
    value = value,
    onValueChange = {
      value = it
      if (enableValidate)
        isError = validators.values.any { func -> !func(value) }
      // call back 將組件內 state 傳出去
      onStateChanged(TextFieldState(value, isError))
    },
    isError = isError
  )


  if (enableValidate)
    validators.filterValues { func -> !func(value) }.forEach { (t, _) ->
      Text(
        text = t,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }


}