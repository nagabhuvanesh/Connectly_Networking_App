package com.connectly.app.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.connectly.app.generated.resources.Res
import com.connectly.app.generated.resources.inter_variable
import org.jetbrains.compose.resources.Font

// Text styles map 1:1 onto the source design's Tailwind fontSize tokens.
class ConnectlyTextStyles(fontFamily: FontFamily) {
    val headlineXl = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = (-0.03).sp)
    val headlineXlMobile = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp, lineHeight = 38.sp, letterSpacing = (-0.025).sp)
    val headlineLg = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 34.sp, letterSpacing = (-0.02).sp)
    val headlineSm = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 28.sp, letterSpacing = (-0.015).sp)
    val subheadingMd = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = (-0.01).sp)
    val bodyLg = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp)
    val bodyMd = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp)
    val labelMd = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.01.sp)
    val labelSm = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 0.04.sp)
}

// Compose Multiplatform loads resource-based fonts through a @Composable API (font bytes are
// decoded into a platform Typeface as part of composition), so — unlike the old Android-only
// version — the styles can no longer live in a top-level `val` and must be read via this
// CompositionLocal, provided once by ConnectlyTheme.
val LocalConnectlyTextStyles = staticCompositionLocalOf<ConnectlyTextStyles> {
    error("No ConnectlyTextStyles provided — wrap content in ConnectlyTheme")
}

@Composable
internal fun connectlyFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_variable, weight = FontWeight.Normal),
    Font(Res.font.inter_variable, weight = FontWeight.Medium),
    Font(Res.font.inter_variable, weight = FontWeight.SemiBold),
    Font(Res.font.inter_variable, weight = FontWeight.Bold),
    Font(Res.font.inter_variable, weight = FontWeight.ExtraBold),
)

@Composable
internal fun connectlyTextStyles(): ConnectlyTextStyles {
    val family = connectlyFontFamily()
    return remember(family) { ConnectlyTextStyles(family) }
}

@Composable
internal fun connectlyTypography(styles: ConnectlyTextStyles): Typography = Typography(
    displayLarge = styles.headlineXl,
    displayMedium = styles.headlineXlMobile,
    headlineLarge = styles.headlineLg,
    headlineMedium = styles.headlineSm,
    headlineSmall = styles.headlineSm,
    titleLarge = styles.headlineSm,
    titleMedium = styles.subheadingMd,
    titleSmall = styles.labelMd,
    bodyLarge = styles.bodyLg,
    bodyMedium = styles.bodyMd,
    bodySmall = styles.labelMd,
    labelLarge = styles.labelMd,
    labelMedium = styles.labelMd,
    labelSmall = styles.labelSm,
)
