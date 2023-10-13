package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.awt.TextField

@Composable
@Preview
fun RichStandaloneKeyGenerator() {
  var text by remember { mutableStateOf("Hello, World!") }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(text = "Input") }
    )
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ){
    Column(
                modifier = Modifier.padding(50.dp)
            ) {
                for (x in 1..5) {
                    val text = remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = text.value,
                        singleLine = true,
                        onValueChange = { text.value = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
  }
//  MaterialTheme {
//
//    Button(onClick = {
//      text = "Hello, Desktop!"
//    }) {
//      Text(text)
//    }
//  }
}