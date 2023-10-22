package view.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ValidatedTextField(
  label: String,
  value: String,
  enableValidate: Boolean,
  errorMessages: List<String>,
  isTrim: Boolean = true,
  onStateChanged: (String) -> Unit,
) {
  var isValidate by remember { mutableStateOf(false) }

  OutlinedTextField(
    modifier = Modifier.fillMaxWidth(),
    label = { Text(label) },
    value = value,
    onValueChange = {
      isValidate = true
      val v = if (isTrim) it.trim() else it
      onStateChanged(v)
    },
    isError = (enableValidate || isValidate) && errorMessages.isNotEmpty()
  )

  if ((enableValidate || isValidate) && errorMessages.isNotEmpty())
    errorMessages.forEach {
      Text(
        text = it,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }
}

