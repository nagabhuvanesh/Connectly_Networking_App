package com.connectly.app.feature.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.ConnectlyPrimaryButton
import com.connectly.app.core.components.QrCodeVisual
import com.connectly.app.core.components.StepperDots
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val title: String,
    val subtitle: String,
)

private val pages = listOf(
    OnboardingPage(
        title = "Your network, organized",
        subtitle = "Keep track of the people you meet at every event without the business card chaos.",
    ),
    OnboardingPage(
        title = "One scan to connect",
        subtitle = "Scan an event QR or someone's profile QR.",
    ),
    OnboardingPage(
        title = "Never forget who you met",
        subtitle = "Find your connections and the events where you met them.",
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit, onSkip: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 20.dp, end = 12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Hub,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(14.dp),
                    )
                }
                Text(
                    "Connectly",
                    style = LocalConnectlyTextStyles.current.subheadingMd,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            if (pagerState.currentPage == pages.lastIndex) {
                Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(50)) {
                    Text(
                        "Step ${pages.size} of ${pages.size}",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    )
                }
            } else {
                TextButton(onClick = onSkip) {
                    Text("Skip", style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val item = pages[page]
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                when (page) {
                    0 -> OnboardingHeroOne()
                    1 -> OnboardingHeroTwo()
                    else -> OnboardingHeroThree()
                }
                Text(
                    item.title,
                    style = LocalConnectlyTextStyles.current.headlineLg,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 28.dp),
                )
                Text(
                    item.subtitle,
                    style = LocalConnectlyTextStyles.current.bodyLg,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            StepperDots(stepCount = pages.size, activeIndex = pagerState.currentPage)
            ConnectlyPrimaryButton(
                text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next",
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinished()
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            )
            if (pagerState.currentPage == pages.lastIndex) {
                TextButton(onClick = onFinished, modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        "I already have an account",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingHeroOne() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(28.dp))
            .padding(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            ProfilePill(
                name = "Rahul Sharma",
                role = "Staff Eng",
                tint = MaterialTheme.colorScheme.primaryContainer,
                compact = true,
            )
            ProfilePill(
                name = "Karthik R.",
                role = "Founding PM",
                tint = MaterialTheme.colorScheme.tertiaryContainer,
                compact = true,
            )
        }
        DottedConnector(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 6.dp))
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSecondaryContainer),
                            )
                            Text(
                                "ACTIVE GROUP",
                                style = LocalConnectlyTextStyles.current.labelSm,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(start = 6.dp),
                            )
                        }
                    }
                    Text("Today  •  18:30", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Filled.SmartToy, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    }
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text("Google AI Meetup", style = LocalConnectlyTextStyles.current.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                        Text("34 Verified Connections", style = LocalConnectlyTextStyles.current.bodyMd, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
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
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                    )
                }
            }
        }
        DottedConnector(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 6.dp))
        ProfilePill(
            name = "Ananya Rao",
            role = "Partner @ Nexus Ventures",
            tint = MaterialTheme.colorScheme.tertiaryContainer,
            verified = true,
            trailingIcon = Icons.Filled.QrCode2,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun OnboardingHeroTwo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(28.dp))
                    .padding(20.dp),
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                                )
                                Text(
                                    "LIVE SENSOR",
                                    style = LocalConnectlyTextStyles.current.labelSm,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(start = 6.dp),
                                )
                            }
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.padding(top = 8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            ) {
                                Icon(Icons.Filled.ConfirmationNumber, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                Text(
                                    "Summit 2025",
                                    style = LocalConnectlyTextStyles.current.labelMd,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(start = 6.dp),
                                )
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.FlashOn, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                        Icon(
                            Icons.Filled.PhotoCamera,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 10.dp).size(20.dp),
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    QrCodeVisual(seed = "onboarding-preview", size = 150.dp)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .border(3.dp, MaterialTheme.colorScheme.surfaceContainerLowest, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Icons.Filled.SmartToy, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(18.dp))
                    }
                    ViewfinderCorners(modifier = Modifier.fillMaxSize())
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.QrCode2, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                            Text(
                                "Instant Dual Decoder",
                                style = LocalConnectlyTextStyles.current.labelMd,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(start = 6.dp),
                            )
                        }
                        Text("READY", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp)
                    .offset(y = 10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(14.dp))
                    Text(
                        "Profile Detected",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(start = 6.dp),
                    )
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(top = 28.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                Text(
                    "Frictionless Exchange",
                    style = LocalConnectlyTextStyles.current.labelSm,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
        }
    }
}

@Composable
private fun OnboardingHeroThree() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(28.dp))
            .padding(16.dp),
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLowest, shape = RoundedCornerShape(50), modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.ConfirmationNumber, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                }
                Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
                    Text("Slush Tech Summit · Helsinki", style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurface)
                    Text("Nov 28 · Stage 3 Networking", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
            }
        }
        DottedConnector(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 6.dp))
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLowest, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("AL", style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurface)
                    }
                    Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Astrid Lindgren", style = LocalConnectlyTextStyles.current.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                            Icon(
                                Icons.Filled.Verified,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 4.dp).size(14.dp),
                            )
                        }
                        Text("Co-Founder · Lumina Climate", style = LocalConnectlyTextStyles.current.bodyMd, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(Icons.Filled.BookmarkBorder, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                }
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Forum, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                Text(
                                    "Memory Anchor",
                                    style = LocalConnectlyTextStyles.current.labelSm,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                            Text("99.4% Match", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.primary)
                        }
                        Text(
                            "\"Discussed Series A syndication & Nordic carbon removal pilot.\"",
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 6.dp),
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 12.dp)) {
                    StatPill(icon = Icons.Filled.Hub, text = "14 Mutuals")
                    StatPill(icon = Icons.Filled.Email, text = "Direct Intros")
                    StatPill(icon = Icons.Filled.Schedule, text = "Added 2h ago")
                }
            }
        }
        DottedConnector(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Sync, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(14.dp))
                Text(
                    "Archived to Slush '24 Roll",
                    style = LocalConnectlyTextStyles.current.labelMd,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
            Text("Synced", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
private fun ProfilePill(
    name: String,
    role: String,
    tint: Color,
    modifier: Modifier = Modifier,
    verified: Boolean = false,
    trailingIcon: ImageVector? = null,
    compact: Boolean = false,
) {
    Surface(color = MaterialTheme.colorScheme.surfaceContainerLowest, shape = RoundedCornerShape(50), modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 6.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(tint),
                contentAlignment = Alignment.Center,
            ) {
                Text(name.take(1), style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurface)
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .let { if (compact) it else it.weight(1f) },
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(name, style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurface)
                    if (verified) {
                        Icon(
                            Icons.Filled.Verified,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(start = 4.dp).size(12.dp),
                        )
                    }
                }
                Text(role, style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (trailingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(trailingIcon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
private fun StatPill(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(50), modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
            Text(text, style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
private fun DottedConnector(
    modifier: Modifier = Modifier,
    height: Dp = 20.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
) {
    Canvas(modifier = modifier.width(2.dp).height(height)) {
        drawLine(
            color = color,
            start = Offset(size.width / 2f, 0f),
            end = Offset(size.width / 2f, size.height),
            strokeWidth = size.width,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
        )
    }
}

@Composable
private fun ViewfinderCorners(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Canvas(modifier = modifier) {
        val cornerLen = size.minDimension * 0.14f
        val strokeW = 3.dp.toPx()
        val w = size.width
        val h = size.height
        drawLine(color, Offset(0f, 0f), Offset(cornerLen, 0f), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(0f, 0f), Offset(0f, cornerLen), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(w, 0f), Offset(w - cornerLen, 0f), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(w, 0f), Offset(w, cornerLen), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(0f, h), Offset(cornerLen, h), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(0f, h), Offset(0f, h - cornerLen), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(w, h), Offset(w - cornerLen, h), strokeW, cap = StrokeCap.Round)
        drawLine(color, Offset(w, h), Offset(w, h - cornerLen), strokeW, cap = StrokeCap.Round)
    }
}
