package com.easyhooon.dari.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.easyhooon.dari.MessageStatus

/**
 * Colors used to communicate a message's status (in-progress / success / error).
 *
 * Each status exposes three roles so the same semantic color can be reused in
 * different UI contexts without compromising contrast:
 *
 * - [container]   — solid background fill, e.g. a selected status filter chip.
 *   Kept identical in both themes because filled chips retain enough contrast
 *   on both light and dark app surfaces.
 * - [onContainer] — text / icon color drawn on top of [container]. Matches the
 *   WCAG contrast requirement against [container] (so the amber chip uses
 *   black while red / green use white).
 * - [onSurface]   — the status color when drawn directly as text on the app
 *   surface (e.g. the status label inside a list row). This role varies per
 *   theme because the Material 500-weight status colors are too bright for a
 *   white light-theme surface but read well on a dimmed dark-theme surface.
 */
internal data class MessageStatusPalette(
    val container: Color,
    val onContainer: Color,
    val onSurface: Color,
)

/**
 * Theme-aware palette for a [MessageStatus]. The [onSurface] shade is picked
 * from the current [MaterialTheme] — we infer dark vs light by checking the
 * luminance of `colorScheme.surface` so this stays correct whether the app is
 * following the system theme or using an explicit override from Dari's own
 * settings.
 */
internal val MessageStatus.palette: MessageStatusPalette
    @Composable
    @ReadOnlyComposable
    get() {
        val isDarkScheme = MaterialTheme.colorScheme.surface.luminance() < 0.5f
        return when (this) {
            MessageStatus.IN_PROGRESS -> MessageStatusPalette(
                container = Color(0xFFFFC107), // amber 500 — bright chip fill
                onContainer = Color.Black, // amber needs dark text for AA contrast
                onSurface = if (isDarkScheme) {
                    Color(0xFFFFD54F) // amber 300 — light enough for dark surface
                } else {
                    Color(0xFFBF360C) // deep orange 900 — dark enough for white
                },
            )
            MessageStatus.SUCCESS -> MessageStatusPalette(
                container = Color(0xFF4CAF50), // green 500
                onContainer = Color.White,
                onSurface = if (isDarkScheme) {
                    Color(0xFF81C784) // green 300
                } else {
                    Color(0xFF2E7D32) // green 800
                },
            )
            MessageStatus.ERROR -> MessageStatusPalette(
                container = Color(0xFFF44336), // red 500
                onContainer = Color.White,
                onSurface = if (isDarkScheme) {
                    Color(0xFFE57373) // red 300
                } else {
                    Color(0xFFC62828) // red 800
                },
            )
        }
    }
