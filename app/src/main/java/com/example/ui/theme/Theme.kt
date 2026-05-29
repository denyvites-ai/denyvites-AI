package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PolishDarkPrimary,
    secondary = PolishDarkSecondary,
    tertiary = PolishDarkTertiary,
    background = PolishDarkBackground,
    surface = PolishDarkSurface,
    onPrimary = PolishDarkOnPrimary,
    onSecondary = PolishDarkOnSecondary,
    onTertiary = PolishDarkOnTertiary,
    onBackground = PolishDarkOnBackground,
    onSurface = PolishDarkOnSurface,
    primaryContainer = PolishDarkPrimaryContainer,
    onPrimaryContainer = PolishDarkOnPrimaryContainer,
    secondaryContainer = PolishDarkSecondaryContainer,
    onSecondaryContainer = PolishDarkOnSecondaryContainer,
    surfaceVariant = PolishDarkSurfaceVariant,
    onSurfaceVariant = PolishDarkOnSurfaceVariant,
    error = PolishDarkError,
    onError = PolishDarkOnError,
    outline = PolishOutline
)

private val LightColorScheme = lightColorScheme(
    primary = PolishPrimary,
    secondary = PolishSecondary,
    tertiary = PolishTertiary,
    background = PolishBackground,
    surface = PolishSurface,
    onPrimary = PolishOnPrimary,
    onSecondary = PolishOnSecondary,
    onTertiary = PolishOnTertiary,
    onBackground = PolishOnBackground,
    onSurface = PolishOnSurface,
    primaryContainer = PolishPrimaryContainer,
    onPrimaryContainer = PolishOnPrimaryContainer,
    secondaryContainer = PolishSecondaryContainer,
    onSecondaryContainer = PolishOnSecondaryContainer,
    surfaceVariant = PolishSurfaceVariant,
    onSurfaceVariant = PolishOnSurfaceVariant,
    error = PolishError,
    onError = PolishOnError,
    outline = PolishOutline
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
