package ls.bdsg.tsamaisa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Temporary theme: Material You colours taken from the phone's wallpaper.
 * Because minSdk is 31 (ADR-002), dynamic colour is always available, so no
 * version check is needed. A proper visual identity comes with the design work.
 */
@Composable
fun TsamaisaTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val colors = if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    MaterialTheme(colorScheme = colors, content = content)
}
