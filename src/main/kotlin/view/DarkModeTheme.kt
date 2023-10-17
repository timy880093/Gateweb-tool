package view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// You can change the colors
val LightTheme = lightColorScheme(
//affect the surfaces of components
    surface = Color(0xffD9D9D9),
    onSurface  = Color(0xff070A52),
//for key components across the UI
    primary = Color(0xffDF2E38),
    secondary = Color.Black,
// that sits on top of primary
    onPrimary = Color.White,
// that sits on top of secondary
    onSecondary = Color(0xffAF2D2D)
)
val DarkTheme = darkColorScheme(
    surface = Color(0xff353535),
    onSurface  = Color(0xffBEBFD1),
    primary = Color(0xff2751A3),
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary =Color(0xff6881D8)
)

@Composable
fun DarkModeTheme(
// Shows which theme are you in
    isDark: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (isDark) DarkTheme else LightTheme // Use the appropriate color list
    ) {
        Surface {
            content()
        }
    }
}