package com.example.worldclock.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F46E5),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = Color(0xFF1E1B4B),

    secondary = Color(0xFF6366F1),
    onSecondary = Color.White,

    background = Color(0xFFF8F9FC),
    onBackground = Color(0xFF18181B),

    surface = Color.White,
    onSurface = Color(0xFF18181B),

    surfaceVariant = Color(0xFFE5E7EB),
    onSurfaceVariant = Color(0xFF52525B)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA5B4FC),
    onPrimary = Color(0xFF1E1B4B),

    primaryContainer = Color(0xFF3730A3),
    onPrimaryContainer = Color(0xFFE0E7FF),

    secondary = Color(0xFFC7D2FE),
    onSecondary = Color(0xFF1E1B4B),

    background = Color(0xFF09090B),
    onBackground = Color(0xFFF4F4F5),

    surface = Color(0xFF18181B),
    onSurface = Color(0xFFF4F4F5),

    surfaceVariant = Color(0xFF27272A),
    onSurfaceVariant = Color(0xFFA1A1AA)
)

@Composable
fun WorldClockTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}