package com.connectly.app.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Mirrors the Tailwind config's borderRadius scale (DEFAULT/lg/xl/full).
object ConnectlyRadius {
    val Default = 4.dp
    val Lg = 8.dp
    val Xl = 12.dp
    val Full = 9999.dp
}

val ConnectlyShapes = Shapes(
    extraSmall = RoundedCornerShape(ConnectlyRadius.Default),
    small = RoundedCornerShape(ConnectlyRadius.Lg),
    medium = RoundedCornerShape(ConnectlyRadius.Xl),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

// Mirrors the Tailwind config's spacing scale (space-xs..space-xl, gutter, margin).
object ConnectlySpacing {
    val Xs = 4.dp
    val Sm = 8.dp
    val Md = 16.dp
    val Lg = 24.dp
    val Xl = 32.dp
    val Gutter = 16.dp
    val Margin = 20.dp
}
