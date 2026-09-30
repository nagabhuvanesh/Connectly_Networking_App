package com.connectly.app.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal actual fun ApplyStatusBarStyle(surfaceColor: Color, darkIcons: Boolean) {
    // No status bar chrome to color in a browser tab.
}
