package com.connectly.app.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal actual fun ApplyStatusBarStyle(surfaceColor: Color, darkIcons: Boolean) {
    // No Android-style status bar chrome to color on iOS.
}
