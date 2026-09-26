package com.connectly.app.feature.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBarsPadding
import com.connectly.app.core.theme.ConnectlyTextStyles
import kotlinx.coroutines.delay

private data class SplashStage(val pct: Float, val label: String)

private val stages = listOf(
    SplashStage(0.64f, "Initializing network..."),
    SplashStage(0.72f, "Syncing executive registry..."),
    SplashStage(0.88f, "Securing tactile peer tokens..."),
    SplashStage(0.97f, "Opening personal space..."),
    SplashStage(1f, "Ready"),
)

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var stageIndex by remember { mutableStateOf(0) }
    val progress by animateFloatAsState(
        targetValue = stages[stageIndex].pct,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "splashProgress",
    )

    LaunchedEffect(Unit) {
        for (i in stages.indices) {
            stageIndex = i
            delay(500)
        }
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 20.dp, vertical = 48.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        "EXECUTIVE SUITE",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(14.dp),
                    )
                    Text(
                        "Encrypted Sync",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(128.dp), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Hub,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(56.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                            .border(2.dp, MaterialTheme.colorScheme.surfaceContainerLowest, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Verified,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 24.dp)) {
                    Text(
                        "Connectly",
                        style = ConnectlyTextStyles.headlineXlMobile,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(start = 8.dp),
                    ) {
                        Text(
                            "v3.2",
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        )
                    }
                }
                Text(
                    "Meet people. Remember connections.",
                    style = ConnectlyTextStyles.subheadingMd,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(top = 20.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    ) {
                        val initials = listOf("SF", "LD", "NYC")
                        val tints = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer,
                        )
                        initials.forEachIndexed { index, initial ->
                            Box(
                                modifier = Modifier
                                    .offset(x = if (index == 0) 0.dp else (-8).dp)
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(tints[index])
                                    .border(1.5.dp, MaterialTheme.colorScheme.surfaceContainerLowest, CircleShape),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(initial.take(2), style = ConnectlyTextStyles.labelSm, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                        Text(
                            "Global Exchange Active",
                            style = ConnectlyTextStyles.labelMd,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 10.dp),
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                stages[stageIndex].label,
                                style = ConnectlyTextStyles.labelSm,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                "${(progress * 100).toInt()}%",
                                style = ConnectlyTextStyles.labelSm,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceContainer),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                            )
                        }
                    }
                }
                Text(
                    "High-Trust Architecture  •  Sub-second Sync",
                    style = ConnectlyTextStyles.labelSm,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}
