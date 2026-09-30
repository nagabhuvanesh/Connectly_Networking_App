package com.connectly.app.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

// A decorative, deterministic pseudo-QR pattern seeded by an identifier (e.g. connectId).
// This is a mock visual, not a scannable code — there is no backend to encode a real
// payload into, and the source designs only need a QR-shaped placeholder on screen.
@Composable
fun QrCodeVisual(
    seed: String,
    modifier: Modifier = Modifier,
    size: Dp = 220.dp,
    moduleCount: Int = 21,
) {
    val foreground = MaterialTheme.colorScheme.onSurface
    val background = MaterialTheme.colorScheme.surfaceContainerLowest
    Canvas(
        modifier = modifier
            .size(size)
            .background(background, MaterialTheme.shapes.medium),
    ) {
        val moduleSize = this.size.width / moduleCount
        val random = Random(seed.hashCode())
        val finderSize = 5

        fun isFinderZone(row: Int, col: Int): Boolean {
            val zones = listOf(
                0 to 0,
                0 to (moduleCount - finderSize),
                (moduleCount - finderSize) to 0,
            )
            return zones.any { (zr, zc) -> row in zr until zr + finderSize && col in zc until zc + finderSize }
        }

        fun drawFinder(row: Int, col: Int) {
            drawRect(
                color = foreground,
                topLeft = androidx.compose.ui.geometry.Offset(col * moduleSize, row * moduleSize),
                size = Size(finderSize * moduleSize, finderSize * moduleSize),
            )
            drawRect(
                color = background,
                topLeft = androidx.compose.ui.geometry.Offset((col + 1) * moduleSize, (row + 1) * moduleSize),
                size = Size((finderSize - 2) * moduleSize, (finderSize - 2) * moduleSize),
            )
            drawRect(
                color = foreground,
                topLeft = androidx.compose.ui.geometry.Offset((col + 2) * moduleSize, (row + 2) * moduleSize),
                size = Size((finderSize - 4) * moduleSize, (finderSize - 4) * moduleSize),
            )
        }

        for (row in 0 until moduleCount) {
            for (col in 0 until moduleCount) {
                if (isFinderZone(row, col)) continue
                if (random.nextFloat() < 0.42f) {
                    drawRect(
                        color = foreground,
                        topLeft = androidx.compose.ui.geometry.Offset(col * moduleSize, row * moduleSize),
                        size = Size(moduleSize, moduleSize),
                    )
                }
            }
        }

        drawFinder(0, 0)
        drawFinder(0, moduleCount - finderSize)
        drawFinder(moduleCount - finderSize, 0)
    }
}
