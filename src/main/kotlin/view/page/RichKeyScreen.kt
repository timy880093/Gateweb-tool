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
import androidx.compose.ui.unit.sp
import utils.isNumber
import view.composable.Space
import view.composable.ValidatedTextField
import viewModel.RichKeyEvent
import viewModel.RichKeyViewModel

@Composable
@Preview
fun RichKeyScreen(viewModel: RichKeyViewModel) {

  val formState by viewModel.formState.collectAsState()

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidatedTextField(
          "統編1", formState.taxId1.text, formState.enableValidate, formState.taxId1.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId1(it)) }
        Space(10)
        ValidatedTextField(
          "統編2", formState.taxId2.text, formState.enableValidate, formState.taxId2.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId2(it)) }
        Space(10)
        ValidatedTextField(
          "統編3", formState.taxId3.text, formState.enableValidate, formState.taxId3.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateTaxId3(it)) }
        Space(10)
        ValidatedTextField(
          "西元年末2碼", formState.yearEnd2.text, formState.enableValidate, formState.yearEnd2.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateYearEnd2(it)) }

      }
      Space(15)
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        ValidatedTextField(
          "本地印表", formState.localPrintCount.text, formState.enableValidate, formState.localPrintCount.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateLocalPrintCount(it)) }
        Space(5)
        ValidatedTextField(
          "CSV 轉檔",
          formState.csvTransformCount.text,
          formState.enableValidate,
          formState.csvTransformCount.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateCsvTransformCount(it)) }
        Space(5)
        ValidatedTextField(
          "TXT 轉檔",
          formState.txtTransformCount.text,
          formState.enableValidate,
          formState.txtTransformCount.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateTxtTransformCount(it)) }
        Space(5)
        ValidatedTextField(
          "傳輸", formState.transferCount.text, formState.enableValidate, formState.transferCount.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateTransferCount(it)) }
        Space(5)
        ValidatedTextField(
          "FTP 傳輸", formState.ftpCount.text, formState.enableValidate, formState.ftpCount.errorMessage()
        ) { viewModel.onChange(RichKeyEvent.UpdateFtpCount(it)) }
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("雲端印表") },
          value = formState.cloudPrintCount.text,
          onValueChange = {},
          enabled = false
        )
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("Email") },
          value = formState.emailCount.text,
          onValueChange = {},
          enabled = false
        )
        Space(5)
        OutlinedTextField(
          modifier = Modifier.fillMaxWidth(),
          label = { Text("Web") },
          value = formState.webCount.text,
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
      Button(onClick = { viewModel.onSubmit() }) { Text(text = "產生", fontSize = 16.sp) }
      Space(5)
      Button(onClick = { viewModel.onClear() }) { Text(text = "清除", fontSize = 16.sp) }
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
          value = formState.key,
          onValueChange = {},
          readOnly = true
        )
        Space(5)
        OutlinedTextField(
          label = { Text("Lock") },
          value = formState.lock,
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