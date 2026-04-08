package com.easyhooon.dari.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/** Chucker-style blue TopBar color, used as the brand primary in both themes. */
val DariBlue = Color(0xFF2D6AB1)

/** Slightly dimmer blue for the dark theme so it doesn't glow on AMOLED. */
private val DariBlueDark = Color(0xFF1F4F87)

private val DariLightColorScheme = lightColorScheme(
    primary = DariBlue,
    onPrimary = Color.White,
    primaryContainer = DariBlue,
    onPrimaryContainer = Color.White,
)

private val DariDarkColorScheme = darkColorScheme(
    primary = DariBlueDark,
    onPrimary = Color.White,
    primaryContainer = DariBlueDark,
    onPrimaryContainer = Color.White,
)

/**
 * Material3 theme wrapper for Dari's internal screens.
 *
 * [darkTheme] accepts a nullable [Boolean] so callers can pass the persisted
 * user override directly: `null` means "follow the system", `true` / `false`
 * mean the user explicitly chose dark / light.
 */
@Composable
internal fun DariTheme(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val isDark = darkTheme ?: isSystemInDarkTheme()
    val colorScheme = if (isDark) DariDarkColorScheme else DariLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

object DariTopBarColors {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun colors(): TopAppBarColors {
        val container = MaterialTheme.colorScheme.primary
        return TopAppBarColors(
            containerColor = container,
            scrolledContainerColor = container,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        )
    }
}
