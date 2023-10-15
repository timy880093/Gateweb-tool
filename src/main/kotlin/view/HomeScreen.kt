package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

private val countryList =
  mutableListOf("India", "Pakistan", "China", "United States")

private val listModifier = Modifier
  .fillMaxSize()
  .background(Color.Gray)
  .padding(10.dp)

private val textStyle = TextStyle(fontSize = 20.sp, color = Color.White)


@Composable
@Preview
fun HomeScreen() {



  Card(
    modifier = Modifier.fillMaxSize(),
    shape = RoundedCornerShape(10.dp),
    elevation = 0.dp,
    backgroundColor = Color.White
  ) {
    Row(modifier = Modifier.fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
        Nav()
      }

      Column(modifier = Modifier.fillMaxWidth().weight(0.7f)) {
//        right()
      }

    }


  }


}


@Composable
fun right() {
  LazyColumn(modifier = Modifier.fillMaxSize().background(Color.Gray).padding(10.dp)) {
    items(mutableListOf("India", "Pakistan", "China", "United States")) { country ->
      Text(text = country, style = TextStyle(fontSize = 20.sp, color = Color.White))
    }
  }
}

