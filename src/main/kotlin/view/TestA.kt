package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun NavCompose() {
  Card(
    modifier = Modifier.fillMaxSize(),
    shape = RoundedCornerShape(10.dp),
    elevation = 0.dp,
    backgroundColor = Color.White
  ) {
    Row(modifier = Modifier.fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
        Row {
          Button(onClick = { println("123") }, modifier = Modifier.fillMaxWidth()){
            Text("1233")
          }
        }
        Row {
          Button(onClick = { println("123") }, modifier = Modifier.fillMaxWidth()){
            Text("1233")
          }
        }
//        Image(
//          painterResource("image/gateweb_logo.png"),
//          contentDescription = "", contentScale = ContentScale.Crop,
//          modifier = Modifier.fillMaxSize()
//        )
      }

      Column(modifier = Modifier.fillMaxWidth().weight(0.7f)) {

      }
    }
  }
}