package com.connectly.app.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.connectly.app.R

// Single variable-font file; each weight is a distinct `Font` entry with its own
// 'wght' axis setting rather than five separate static TTFs.
@OptIn(ExperimentalTextApi::class)
val InterFontFamily = FontFamily(
    Font(R.font.inter_variable, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.inter_variable, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.inter_variable, weight = FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(R.font.inter_variable, weight = FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
    Font(R.font.inter_variable, weight = FontWeight.ExtraBold, variationSettings = FontVariation.Settings(FontVariation.weight(800))),
)

// Text styles map 1:1 onto the source design's Tailwind fontSize tokens.
object ConnectlyTextStyles {
    val headlineXl = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = (-0.03).sp)
    val headlineXlMobile = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp, lineHeight = 38.sp, letterSpacing = (-0.025).sp)
    val headlineLg = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 34.sp, letterSpacing = (-0.02).sp)
    val headlineSm = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 28.sp, letterSpacing = (-0.015).sp)
    val subheadingMd = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = (-0.01).sp)
    val bodyLg = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp)
    val bodyMd = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp)
    val labelMd = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.01.sp)
    val labelSm = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 0.04.sp)
}

val ConnectlyTypography = Typography(
    displayLarge = ConnectlyTextStyles.headlineXl,
    displayMedium = ConnectlyTextStyles.headlineXlMobile,
    headlineLarge = ConnectlyTextStyles.headlineLg,
    headlineMedium = ConnectlyTextStyles.headlineSm,
    headlineSmall = ConnectlyTextStyles.headlineSm,
    titleLarge = ConnectlyTextStyles.headlineSm,
    titleMedium = ConnectlyTextStyles.subheadingMd,
    titleSmall = ConnectlyTextStyles.labelMd,
    bodyLarge = ConnectlyTextStyles.bodyLg,
    bodyMedium = ConnectlyTextStyles.bodyMd,
    bodySmall = ConnectlyTextStyles.labelMd,
    labelLarge = ConnectlyTextStyles.labelMd,
    labelMedium = ConnectlyTextStyles.labelMd,
    labelSmall = ConnectlyTextStyles.labelSm,
)
