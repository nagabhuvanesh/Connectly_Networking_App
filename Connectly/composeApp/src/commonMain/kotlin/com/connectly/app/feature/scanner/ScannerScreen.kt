package com.connectly.app.feature.scanner

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.QrCodeVisual
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.repository.ServiceLocator
import kotlinx.coroutines.delay

private enum class ScanState { SEARCHING, DETECTED }

@Composable
fun ScannerScreen(onConnected: (String) -> Unit) {
    var showMyCode by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Scan to Connect",
                style = LocalConnectlyTextStyles.current.headlineLg,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
            )
            Text(
                "Point your camera at someone's Connectly code",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, start = 20.dp, end = 20.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    )
                    Text(
                        "Sensor Active",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
                IconButton(onClick = { /* mock data: no real camera flash to control */ }) {
                    Icon(Icons.Filled.FlashOn, contentDescription = "Toggle flash", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                ScanViewfinder(onConnected = onConnected)
            }

            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoTile(
                    icon = Icons.Filled.People,
                    label = "12 nearby",
                    modifier = Modifier.weight(1f),
                )
                InfoTile(
                    icon = Icons.Filled.Bluetooth,
                    label = "Bluetooth + NFC",
                    modifier = Modifier.weight(1f),
                )
            }

            OutlinedButton(
                onClick = { showMyCode = true },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp),
            ) {
                Icon(Icons.Filled.QrCode2, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(
                    "Show My QR Code",
                    style = LocalConnectlyTextStyles.current.labelMd,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Box(modifier = Modifier.padding(bottom = 20.dp))
        }

        if (showMyCode) {
            MyCodeOverlay(onDismiss = { showMyCode = false })
        }
    }
}

@Composable
private fun InfoTile(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
        Text(
            label,
            style = LocalConnectlyTextStyles.current.labelMd,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@Composable
private fun ScanViewfinder(onConnected: (String) -> Unit) {
    var scanState by remember { mutableStateOf(ScanState.SEARCHING) }
    val scanTarget = remember { ServiceLocator.peopleRepository.scanTarget() }

    LaunchedEffect(Unit) {
        delay(2600)
        scanState = ScanState.DETECTED
        delay(900)
        onConnected(scanTarget.id)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "scan-line")
    val lineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1600, easing = LinearEasing), RepeatMode.Restart),
        label = "scan-line-offset",
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
            Text(
                "Auto-detect enabled",
                style = LocalConnectlyTextStyles.current.labelSm,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .aspectRatio(1f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(24.dp)),
            )
            CornerBracket(alignment = Alignment.TopStart)
            CornerBracket(alignment = Alignment.TopEnd)
            CornerBracket(alignment = Alignment.BottomStart)
            CornerBracket(alignment = Alignment.BottomEnd)

            if (scanState == ScanState.SEARCHING) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = (lineOffset * 220).dp)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
                )
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                if (scanState == ScanState.SEARCHING) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    Text(
                        "Searching for a code...",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                } else {
                    Text(
                        "Code detected — connecting to ${scanTarget.name}",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.CornerBracket(alignment: Alignment) {
    val color = MaterialTheme.colorScheme.primary
    val length = 28.dp
    val thickness = 3.dp
    val isTop = alignment == Alignment.TopStart || alignment == Alignment.TopEnd
    val isStart = alignment == Alignment.TopStart || alignment == Alignment.BottomStart
    Box(modifier = Modifier.align(alignment).size(length)) {
        Box(
            modifier = Modifier
                .align(if (isTop) Alignment.TopStart else Alignment.BottomStart)
                .size(width = length, height = thickness)
                .background(color, RoundedCornerShape(2.dp)),
        )
        Box(
            modifier = Modifier
                .align(if (isStart) Alignment.TopStart else Alignment.TopEnd)
                .size(width = thickness, height = length)
                .background(color, RoundedCornerShape(2.dp)),
        )
    }
}

@Composable
private fun MyCodeOverlay(onDismiss: () -> Unit) {
    val profile by ServiceLocator.profileRepository.profile.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false) { },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(24.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                QrCodeVisual(seed = profile.connectId)
                Text(
                    profile.name,
                    style = LocalConnectlyTextStyles.current.headlineSm,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 16.dp),
                )
                Text(
                    "Let others scan this to connect with you",
                    style = LocalConnectlyTextStyles.current.bodyMd,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                )
            }
        }
    }
}
