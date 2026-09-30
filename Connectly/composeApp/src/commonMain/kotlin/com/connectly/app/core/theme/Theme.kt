package com.connectly.app.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

// Connectly is a single, fixed light brand palette in the source designs (no dark-mode
// variant was provided in the mockups), so we expose one ColorScheme regardless of
// system theme rather than guessing at a dark palette.
private val ConnectlyColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = ErrorColor,
    onError = OnErrorColor,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceDim = SurfaceDim,
    surfaceBright = SurfaceBright,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    outline = Outline,
    outlineVariant = OutlineVariant,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    surfaceTint = SurfaceTint,
)

@Composable
fun ConnectlyTheme(content: @Composable () -> Unit) {
    val colorScheme = ConnectlyColorScheme
    ApplyStatusBarStyle(surfaceColor = colorScheme.surface, darkIcons = true)

    val textStyles = connectlyTextStyles()
    CompositionLocalProvider(LocalConnectlyTextStyles provides textStyles) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = connectlyTypography(textStyles),
            shapes = ConnectlyShapes,
            content = content,
        )
    }
}

// Status bar / navigation bar chrome styling is a platform-specific concept (Android only,
// today) — no-op on iOS and web where there's no equivalent system chrome to color.
@Composable
internal expect fun ApplyStatusBarStyle(surfaceColor: Color, darkIcons: Boolean)
