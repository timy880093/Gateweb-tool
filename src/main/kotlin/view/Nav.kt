package view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun Nav() {
  val navigator = rememberNavigator()
  NavHost(
    // Assign the navigator to the NavHost
    navigator = navigator,
    // Navigation transition for the scenes in this NavHost, this is optional
    navTransition = NavTransition(),
    // The start destination
    initialRoute = "/home",
  ) {
    // Define a scene to the navigation graph
    scene(
      // Scene's route path
      route = "/home",
      // Navigation transition for this scene, this is optional
      navTransition = NavTransition(),
    ) {
      Text(text = "Hello!")
    }
  }


  mutableListOf("產生 Rich standalone key", "456").forEach {
    Button(modifier = Modifier.fillMaxWidth().height(40.dp),
      shape = RoundedCornerShape(1.dp),
      colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
      onClick = {
//        navigator.navigate()
        println(it)
      }) {
      Text(it)
    }
  }
}