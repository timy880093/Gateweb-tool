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
import service.RichService

@Composable
@Preview
fun RichKeyScreen2() {
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

  var isError by remember { mutableStateOf(false) }

  Column {
    Row {
      Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("統編1") },
          value = taxId1,
          onValueChange = {
            taxId1 = it
            isError = it.isBlank()
          },
          isError = isError
        )
        if (isError) {
          Text(
            text = "統編必填",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
          )
        }

        Space(10)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("統編2") },
          value = taxId2,
          onValueChange = { taxId2 = it }
        )
        Space(10)
        TextField(
          modifier = Modifier.fillMaxWidth().height(50.dp),
          label = { Text("統編3") },
          value = taxId3,
          onValueChange = { taxId3 = it }
        )

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
        isError = taxId1.isBlank()
        if (isError) return@Button

        val permit = Permit(
          success = true,
          businessNo = listOf(taxId1, taxId2, taxId3),
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
