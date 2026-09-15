package com.example.worldclock.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/*
 * =========================================================
 * LIGHT COLOR SCHEME
 * =========================================================
 */

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF3F51B5),
    onPrimary = androidx.compose.ui.graphics.Color.White,

    primaryContainer = androidx.compose.ui.graphics.Color(0xFFDDE2FF),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF00145C),

    secondary = androidx.compose.ui.graphics.Color(0xFF5B5D72),
    onSecondary = androidx.compose.ui.graphics.Color.White,

    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFE1E1F9),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF181A2C),

    background = androidx.compose.ui.graphics.Color(0xFFF9F9FF),
    onBackground = androidx.compose.ui.graphics.Color(0xFF1A1B20),

    surface = androidx.compose.ui.graphics.Color(0xFFF9F9FF),
    onSurface = androidx.compose.ui.graphics.Color(0xFF1A1B20),

    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE3E2EC),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF46464F),

    outline = androidx.compose.ui.graphics.Color(0xFF777680)
)

/*
 * =========================================================
 * DARK COLOR SCHEME
 * =========================================================
 */

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFBBC3FF),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF162778),

    primaryContainer = androidx.compose.ui.graphics.Color(0xFF293A8A),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFDDE2FF),

    secondary = androidx.compose.ui.graphics.Color(0xFFC5C5DD),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF2D2F42),

    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF444559),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFFE1E1F9),

    background = androidx.compose.ui.graphics.Color(0xFF111318),
    onBackground = androidx.compose.ui.graphics.Color(0xFFE3E2E9),

    surface = androidx.compose.ui.graphics.Color(0xFF111318),
    onSurface = androidx.compose.ui.graphics.Color(0xFFE3E2E9),

    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF45464F),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFC6C6D0),

    outline = androidx.compose.ui.graphics.Color(0xFF90909A)
)

/*
 * =========================================================
 * WORLD CLOCK THEME
 * =========================================================
 */

@Composable
fun WorldClockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors =

        if (darkTheme) {
            DarkColors
        } else {
            LightColors
        }


    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}