package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Permit
import model.TextFieldState
import org.slf4j.LoggerFactory
import service.RichService

@Composable
@Preview
fun RichKeyScreen() {
  val log = LoggerFactory.getLogger("RichKeyScreen")

  val richService = RichService()

  var taxId1 by remember { mutableStateOf("") }
  var taxId2 by remember { mutableStateOf("00000000") }
  var taxId3 by remember { mutableStateOf("00000000") }
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

  var taxId1State by remember { mutableStateOf(TextFieldState("", false)) }
  var taxId2State by remember { mutableStateOf(TextFieldState("00000000", false)) }
  var taxId3State by remember { mutableStateOf(TextFieldState("00000000", false)) }

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidationTextField("統編1", "統編有誤", validateIsNotBlank) { taxId1State = it }
        Space(10)
        ValidationTextField("統編2", "統編有誤", validateIsNotBlank) { taxId2State = it }
        Space(10)
        ValidationTextField("統編3", "統編有誤", validateIsNotBlank) { taxId3State = it }
      }
      Space(20)
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("本地印表") },
          value = localPrintCount,
          onValueChange = { localPrintCount = it },
        )
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
//        isError = taxId1.isBlank()
        if (taxId1State.isError || taxId2State.isError || taxId3State.isError) {
          log.warn("validate error")
          return@Button
        }

        val permit = Permit(
          success = true,
          businessNo = listOf(taxId1State.text, taxId2State.text, taxId3State.text),
          localPrintAuthorizedQuantity = localPrintCount.toInt(),
          csvTransformAuthorizedQuantity = csvTransformCount.toInt(),
          txtTransformAuthorizedQuantity = txtTransformCount.toInt(),
          transferAuthorizedQuantity = transferCount.toInt(),
          ftpGrabberAuthorizedQuantity = ftpCount.toInt(),
        )
        val (k, l) = richService.generateStandaloneKey(permit, yearEnd)
        key = k
        lock = l
        println("generate OK")
      }) { Text("產生") }
    }
    Space(20)
    Row(
      modifier = Modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
          modifier = Modifier.height(50.dp),
          label = { Text("Key") },
          value = key,
          onValueChange = { it },
          readOnly = true
        )
        Space(5)
        TextField(
          modifier = Modifier.height(50.dp),
          label = { Text("Lock") },
          value = lock,
          onValueChange = { it },
          readOnly = true
        )
      }

    }
  }


}

@Composable
private fun Space(int: Int) {
  Spacer(modifier = Modifier.padding(int.dp))
}

@Composable
fun ValidationTextField(
  label: String,
  errorMessage: String? = null,
  validationFunction: ((String) -> Boolean)? = null,
  onStateChanged: (TextFieldState) -> Unit
) {
  var text by remember { mutableStateOf("") }
  var isError by remember { mutableStateOf(false) }
//  val msg by remember { mutableStateOf(errorMessage) }
  TextField(
    modifier = Modifier.fillMaxWidth().height(50.dp),
    label = { Text(label) },
    value = text,
    onValueChange = {
      text = it
      validationFunction?.let { func -> isError = !func(it) }
      // call back 將組件內 state 傳出去
      onStateChanged(TextFieldState(text, isError))
    },
    isError = isError
  )
  if (isError) {
    errorMessage?.let {
      Text(
        text = it,
        color = Color.Red,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
      )
    }
  }
}

val validateIsNotBlank: (String) -> Boolean = {
  it.isNotBlank()
}