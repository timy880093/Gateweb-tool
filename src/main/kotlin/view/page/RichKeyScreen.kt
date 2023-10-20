package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import utils.isNumber
import view.composable.Space
import view.composable.ValidatedTextField
import viewModel.RichKeyEvent
import viewModel.RichKeyViewModel

@Composable
@Preview
fun RichKeyScreen(viewModel: RichKeyViewModel) {

  val enableValidate by viewModel.enableValidate.collectAsState()
  val taxId1 by viewModel.taxId1.collectAsState()
  val taxId2 by viewModel.taxId2.collectAsState()
  val taxId3 by viewModel.taxId3.collectAsState()
  val yearEnd2 by viewModel.yearEnd2.collectAsState()
  val localPrintCount by viewModel.localPrintCount.collectAsState()
  val csvTransformCount by viewModel.csvTransformCount.collectAsState()
  val txtTransformCount by viewModel.txtTransformCount.collectAsState()
  val transferCount by viewModel.transferCount.collectAsState()
  val ftpCount by viewModel.ftpCount.collectAsState()
  val cloudPrintCount by viewModel.cloudPrintCount.collectAsState()
  val emailCount by viewModel.emailCount.collectAsState()
  val webCount by viewModel.webCount.collectAsState()
  val key by viewModel.key.collectAsState()
  val lock by viewModel.lock.collectAsState()

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidatedTextField(
          "統編1", taxId1.text, enableValidate, listOf(
            validateIsNotBlank to "必填",
            validateIs8Words to "必須 8 碼數字"
          )
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId1(it)) }
        Space(10)
        ValidatedTextField(
          "統編2", taxId2.text, enableValidate, listOf(
            validateIsNotBlank to "必填",
            validateIs8Words to "必須 8 碼數字"
          )
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId2(it)) }
        Space(10)
        ValidatedTextField(
          "統編3", taxId3.text, enableValidate, listOf(
            validateIsNotBlank to "必填",
            validateIs8Words to "必須 8 碼數字"
          )
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId3(it)) }
        Space(10)
        ValidatedTextField(
          "西元年末2碼", yearEnd2.text, enableValidate, listOf(
            validateIsNotBlank to "必填",
            validateIs2Words to "必須 2 碼數字"
          )
        ) { viewModel.onChange(RichKeyEvent.UpdateYearEnd2(it)) }

      }
      Space(15)
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidatedTextField(
          "本地印表", localPrintCount.text, enableValidate, listOf(validateIsNumberOrEmpty to "必須數字")
        ) { viewModel.onChange(RichKeyEvent.UpdateLocalPrintCount(it)) }
        Space(5)
        ValidatedTextField(
          "CSV 轉檔", csvTransformCount.text, enableValidate, listOf(validateIsNumberOrEmpty to "必須數字")
        ) { viewModel.onChange(RichKeyEvent.UpdateCsvTransformCount(it)) }
        Space(5)
        ValidatedTextField(
          "TXT 轉檔", txtTransformCount.text, enableValidate, listOf(validateIsNumberOrEmpty to "必須數字")
        ) { viewModel.onChange(RichKeyEvent.UpdateTxtTransformCount(it)) }
        Space(5)
        ValidatedTextField(
          "傳輸", transferCount.text, enableValidate, listOf(validateIsNumberOrEmpty to "必須數字")
        ) { viewModel.onChange(RichKeyEvent.UpdateTransferCount(it)) }
        Space(5)
        ValidatedTextField(
          "FTP 傳輸", ftpCount.text, enableValidate, listOf(validateIsNumberOrEmpty to "必須數字")
        ) { viewModel.onChange(RichKeyEvent.UpdateFtpCount(it)) }
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("雲端印表") },
          value = cloudPrintCount.text,
          onValueChange = {},
          enabled = false
        )
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("Email") },
          value = emailCount.text,
          onValueChange = {},
          enabled = false
        )
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("Web") },
          value = webCount.text,
          onValueChange = {},
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
      Button(onClick = { viewModel.generate() }) { Text("產生") }
      Space(5)
      Button(onClick = { viewModel.reset() }) { Text("清除") }
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
  it.length == 8 && it.isNumber()
}

val validateIs2Words: (String) -> Boolean = {
  it.length == 2 && it.isNumber()
}

val validateIsNumberOrEmpty: (String) -> Boolean = {
  it.isNumber() || it.isEmpty()
}