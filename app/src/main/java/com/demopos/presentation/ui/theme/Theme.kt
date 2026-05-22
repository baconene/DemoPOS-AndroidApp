package com.demopos.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6750a4),
    secondary = Color(0xFF625b71),
    tertiary = Color(0xFF7d5260)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750a4),
    secondary = Color(0xFF625b71),
    tertiary = Color(0xFF7d5260)
)

@Composable
fun DemoPOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
