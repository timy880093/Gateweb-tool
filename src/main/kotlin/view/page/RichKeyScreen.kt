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
import model.Permit
import model.TextFieldState
import org.koin.compose.koinInject
import org.slf4j.LoggerFactory
import service.RichService
import view.Space
import view.ValidationTextField

@Composable
@Preview
fun RichKeyScreen() {
  val log = LoggerFactory.getLogger("RichKeyScreen")
  val richService = koinInject<RichService>()

  var validate by remember { mutableStateOf(false) }
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
  var localPrintCountState by remember { mutableStateOf(TextFieldState("0", false)) }

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidationTextField(
          "統編1", taxId1State.text, validate,
          mapOf("統編必填" to validateIsNotBlank, "統編格式有誤" to validateIs8Words)
        ) { taxId1State = it }
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
        ValidationTextField(
          "本地印表", localPrintCountState.text, validate,
          mapOf("必填" to validateIsNotBlank, "必須為數字" to validateIsNumber)
        ) { localPrintCountState = it }
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
        println(taxId1State.isError)
        println(taxId2State.isError)
        println(taxId3State.isError)
        validate = true
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