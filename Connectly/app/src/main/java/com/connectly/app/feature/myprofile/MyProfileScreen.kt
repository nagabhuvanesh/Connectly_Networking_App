package com.connectly.app.feature.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.AvatarWithPresence
import com.connectly.app.core.components.ConnectlyPrimaryButton
import com.connectly.app.core.components.ConnectlySecondaryButton
import com.connectly.app.core.components.QrCodeVisual
import com.connectly.app.core.components.TagChip
import com.connectly.app.core.components.ToastPill
import com.connectly.app.core.theme.ConnectlyTextStyles
import com.connectly.app.data.model.Presence
import com.connectly.app.data.repository.ServiceLocator

@Composable
fun MyProfileScreen() {
    val profile by ServiceLocator.profileRepository.profile.collectAsState()
    var showEditToast by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
        ) {
            Text("My Card", style = ConnectlyTextStyles.headlineLg, color = MaterialTheme.colorScheme.onSurface)
            Text(
                "Share this with anyone you meet",
                style = ConnectlyTextStyles.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 20.dp),
            )

            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.clickable { showEditToast = true },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Edit profile",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp),
                                )
                                Text(
                                    "Edit",
                                    style = ConnectlyTextStyles.labelSm,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        AvatarWithPresence(url = profile.avatarUrl, presence = Presence.ONLINE, size = 88.dp)
                        Text(
                            profile.name,
                            style = ConnectlyTextStyles.headlineSm,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 12.dp),
                        )
                        Text(
                            "${profile.role} · ${profile.company}",
                            style = ConnectlyTextStyles.bodyMd,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            profile.location,
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp),
                        )

                        if (profile.linkedinHandle.isNotBlank()) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 12.dp)) {
                                Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(50)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(14.dp)
                                                .background(Color(0xFF0A66C2), RoundedCornerShape(3.dp)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text("in", style = ConnectlyTextStyles.labelSm, color = Color.White)
                                        }
                                        Text(
                                            profile.linkedinHandle,
                                            style = ConnectlyTextStyles.labelSm,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(start = 6.dp),
                                        )
                                    }
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier.padding(start = 8.dp),
                                ) {
                                    Text(
                                        "Direct Connect",
                                        style = ConnectlyTextStyles.labelSm,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
            ) {
                Column {
                    Text("My Personal QR", style = ConnectlyTextStyles.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        "Let anyone scan this to save your card instantly",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    )
                    Text(
                        "Ready to scan",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 6.dp),
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                ) {
                    QrCodeVisual(seed = profile.connectId)
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(top = 16.dp),
                    ) {
                        Text(
                            "${profile.name} · ${profile.role}",
                            style = ConnectlyTextStyles.labelMd,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        )
                    }
                    Text(
                        profile.connectId,
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 20.dp)) {
                ConnectlyPrimaryButton(
                    text = "Share",
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            Icons.Filled.IosShare,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                    },
                    modifier = Modifier.weight(1f),
                )
                ConnectlySecondaryButton(
                    text = "Add to Wallet",
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Wallet,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
                    .padding(14.dp),
            ) {
                Icon(Icons.Filled.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        "Always accessible offline",
                        style = ConnectlyTextStyles.labelMd,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        "Your card and QR code work even without a connection.",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (profile.interestTags.isNotEmpty()) {
                Text(
                    "Interests",
                    style = ConnectlyTextStyles.headlineSm,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(profile.interestTags) { tag -> TagChip(text = tag) }
                }
            }
        }

        ToastPill(
            message = "Opening profile editor...",
            visible = showEditToast,
            onDismiss = { showEditToast = false },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp),
        )
    }
}
