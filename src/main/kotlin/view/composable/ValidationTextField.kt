package view.composable

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
fun ValidationTextField(
  label: String,
  defaultValue: String,
  defaultValidate: Boolean,
  validators: Map<String, (String) -> Boolean>,
  onStateChanged: (TextFieldState) -> Unit
) {
  var value by remember { mutableStateOf(defaultValue) }
  var enableValidate by remember { mutableStateOf(defaultValidate) }
  var list by remember { mutableStateOf(value.isError2(validators)) }
  var isError by remember { mutableStateOf(list.isNotEmpty()) }

  OutlinedTextField(
    modifier = Modifier.fillMaxWidth(),
    label = { Text(label) },
    value = value,
    onValueChange = {
      value = it
      enableValidate = true
      list = value.isError2(validators)
      isError = list.isNotEmpty()
      // call back 將組件內 state 傳出去
      onStateChanged(TextFieldState(value, isError))
    },
    isError = enableValidate && isError
  )

  if (enableValidate)
    list.forEach {
      Text(
        text = it,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }

}

@Composable
fun ValidatedTextField(
  label: String,
  value: String,
  enableValidate: Boolean,
  validators: List<Pair<(String) -> Boolean, String>>,
  onStateChanged: (TextFieldState) -> Unit
) {
  var isValidate by remember { mutableStateOf(false) }
  var isError by remember { mutableStateOf(value.isError(validators)) }
  onStateChanged(TextFieldState(value, isError))

  OutlinedTextField(
    modifier = Modifier.fillMaxWidth(),
    label = { Text(label) },
    value = value,
    onValueChange = {
      isValidate = true
      isError = it.isError(validators)
      onStateChanged(TextFieldState(it, isError))
    },
    isError = (enableValidate || isValidate) && isError
  )

  if ((enableValidate || isValidate) && isError)
    validators.forEach { (validator, message) ->
      if (validator(value)) return@forEach
      Text(
        text = message,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }
}

private fun String.isError2(validators: Map<String, (String) -> Boolean>): Set<String> {
  return validators.filterValues { func -> !func(this) }.keys
}

private fun String.isError(validators: List<Pair<(String) -> Boolean, String>>): Boolean {
  return validators.any { (validator, _) -> !validator(this) }
}
