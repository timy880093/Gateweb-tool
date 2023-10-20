package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.TextFieldState
import org.slf4j.LoggerFactory
import view.Space
import view.ValidatedTextField
import view.ValidationTextField
import viewModel.RichKeyEvent
import viewModel.RichKeyViewModel

@Composable
@Preview
fun RichKeyScreen(viewModel: RichKeyViewModel) {
  val log = LoggerFactory.getLogger("RichKeyScreen")

  var filedValue by remember { mutableStateOf(TextFieldState("", false)) }
  var textValue by remember { mutableStateOf("") }
  var isValidationEnabled by remember { mutableStateOf(false) }

  val validate by viewModel.enableValidate.collectAsState()
  var yearEnd by remember { mutableStateOf("00") }
  var localPrintCount by remember { mutableStateOf("0") }
  var cloudPrintCount by remember { mutableStateOf("0") }
  var csvTransformCount by remember { mutableStateOf("0") }
  var txtTransformCount by remember { mutableStateOf("0") }
  var transferCount by remember { mutableStateOf("0") }
  var ftpCount by remember { mutableStateOf("0") }
  var emailCount by remember { mutableStateOf("0") }
  var webCount by remember { mutableStateOf("0") }
  var key by remember { mutableStateOf("") }
  var lock by remember { mutableStateOf("") }

  val taxId1State by viewModel.taxId1.collectAsState()
  var taxId2State by remember { mutableStateOf(TextFieldState("00000000", false)) }
  var taxId3State by remember { mutableStateOf(TextFieldState("00000000", false)) }
  var localPrintCountState by remember { mutableStateOf(TextFieldState("0", false)) }

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {

        ValidatedTextField(
          "Input", isValidationEnabled, listOf(
            validateIsNotBlank to "Input cannot be empty",
            validateIs8Words to "Input must be at least 5 characters long"
          )
        ) { field -> filedValue = field }

        ValidatedTextField(
          "Input", validate, listOf(
            validateIsNotBlank to "Input cannot be empty",
            validateIs8Words to "Input must be at least 5 characters long"
          )
        ) { viewModel.updateField(RichKeyEvent.UpdateTaxId1(it)) }

        ValidationTextField(
          "統編1", taxId1State.text, validate,
          mapOf("統編必填" to validateIsNotBlank, "統編格式有誤" to validateIs8Words)
        ) { viewModel.updateField(RichKeyEvent.UpdateTaxId1(it)) }

//        ValidationTextField("統編1", "統編有誤", validateIsNotBlank) { taxId1State = it }
        Space(10)
        ValidationTextField(
          "統編2", taxId2State.text, validate,
          mapOf("統編必填" to validateIsNotBlank, "統編格式有誤" to validateIs8Words)
        ) { taxId2State = it }
        Space(10)
        ValidationTextField(
          "統編3", taxId3State.text, validate,
          mapOf("統編必填" to validateIsNotBlank, "統編格式有誤" to validateIs8Words)
        ) { taxId3State = it }
      }
      Space(20)
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
//        TextField(
//          modifier = Modifier.fillMaxWidth().height(50.dp),
//          label = { Text("本地印表") },
//          value = localPrintCount,
//          onValueChange = { localPrintCount = it },
//        )
//        ValidationTextField(
//          "本地印表", localPrintCountState.text, validate,
//          mapOf("必填" to validateIsNotBlank, "必須為數字" to validateIsNumber)
//        ) { localPrintCountState = it }
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("CSV 轉檔") },
          value = csvTransformCount,
          onValueChange = { csvTransformCount = it },
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("TXT 轉檔") },
          value = txtTransformCount,
          onValueChange = { txtTransformCount = it },
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("傳輸") },
          value = transferCount,
          onValueChange = { transferCount = it },
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("FTP 傳輸") },
          value = ftpCount,
          onValueChange = { ftpCount = it }
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("雲端印表") },
          value = cloudPrintCount,
          onValueChange = { cloudPrintCount = it },
          enabled = false
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("Email") },
          value = emailCount,
          onValueChange = { emailCount = it },
          enabled = false
        )
        Space(5)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("Web") },
          value = webCount,
          onValueChange = { webCount = it },
          enabled = false
        )

      }
    }
    Space(20)
    Row(
      modifier = Modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Button(onClick = {
        isValidationEnabled = true
        viewModel.onClick()
      }) { Text("產生") }
    }
    Space(20)
    Row(
      modifier = Modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
          label = { Text("Key") },
          value = key,
          onValueChange = {},
          readOnly = true
        )
        Space(5)
        OutlinedTextField(
          label = { Text("Lock") },
          value = lock,
          onValueChange = {},
          readOnly = true
        )
      }

    }
  }


}


val validateIsNotBlank: (String) -> Boolean = {
  it.isNotBlank()
}

val validateIs8Words: (String) -> Boolean = {
  it.length == 8
}

val validateIsNumber: (String) -> Boolean = {
  try {
    it.toInt()
    true
  } catch (e: Exception) {
    false
  }
}