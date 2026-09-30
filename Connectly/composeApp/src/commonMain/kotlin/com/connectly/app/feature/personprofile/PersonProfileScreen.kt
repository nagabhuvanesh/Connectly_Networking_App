package com.connectly.app.feature.personprofile

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.AvatarWithPresence
import com.connectly.app.core.components.ConnectlyPrimaryButton
import com.connectly.app.core.components.ConnectlySecondaryButton
import com.connectly.app.core.components.SubScreenHeader
import com.connectly.app.core.components.TagChip
import com.connectly.app.core.components.ToastPill
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.model.Person
import com.connectly.app.data.repository.ServiceLocator

@Composable
fun PersonProfileScreen(personId: String, onBack: () -> Unit) {
    var person by remember { mutableStateOf<Person?>(null) }
    var isFavorite by remember { mutableStateOf(false) }
    var showSavedToast by remember { mutableStateOf(false) }

    LaunchedEffect(personId) {
        person = ServiceLocator.peopleRepository.getPerson(personId)
        isFavorite = person?.isFavorite ?: false
    }

    val current = person ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SubScreenHeader(
                title = "Connection Details",
                onBack = onBack,
                trailingContent = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            if (isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    IconButton(onClick = { showSavedToast = true }) {
                        Icon(
                            Icons.Filled.IosShare,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )

            if (current.isVip) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 8.dp, end = 20.dp),
                ) {
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                            Icon(
                                Icons.Filled.VerifiedUser,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(14.dp),
                            )
                            Text(
                                "Executive Tier Contact",
                                style = LocalConnectlyTextStyles.current.labelSm,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(start = 6.dp),
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AvatarWithPresence(url = current.avatarUrl, presence = current.presence, size = 72.dp)
                    if (current.connectId.isNotBlank()) {
                        Column(horizontalAlignment = Alignment.End) {
                            Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(50)) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
                                    Icon(Icons.Filled.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(11.dp))
                                    Text(
                                        "Vault Synced",
                                        style = LocalConnectlyTextStyles.current.labelSm,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(start = 4.dp),
                                    )
                                }
                            }
                            Text(
                                "ID: ${current.connectId}",
                                style = LocalConnectlyTextStyles.current.labelSm,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(current.name, style = LocalConnectlyTextStyles.current.headlineLg, color = MaterialTheme.colorScheme.onSurface)
                        if (current.isVerified) {
                            Icon(
                                Icons.Filled.Verified,
                                contentDescription = "Verified",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 6.dp).size(18.dp),
                            )
                        }
                    }
                    Text(
                        "${current.role} · ${current.company}",
                        style = LocalConnectlyTextStyles.current.bodyMd,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    if (current.location.isNotBlank() || current.metDateLabel.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                            if (current.location.isNotBlank()) {
                                Icon(Icons.Filled.PinDrop, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                            }
                            Text(
                                buildString {
                                    if (current.location.isNotBlank()) append(current.location)
                                    if (current.metDateLabel.isNotBlank()) {
                                        if (isNotEmpty()) append(" · ")
                                        append("Connected ${current.metDateLabel}")
                                    }
                                },
                                style = LocalConnectlyTextStyles.current.labelSm,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = if (current.location.isNotBlank()) 4.dp else 0.dp),
                            )
                        }
                    }
                }

                if (current.linkedinHandle != null || current.email != null || current.twitterHandle != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 14.dp),
                    ) {
                        current.linkedinHandle?.let { ContactChip(text = it) }
                        current.email?.let { ContactChip(text = it, icon = Icons.Filled.Email) }
                        current.twitterHandle?.let { ContactChip(text = it) }
                    }
                }

                Row(modifier = Modifier.padding(top = 16.dp)) {
                    ConnectlyPrimaryButton(
                        text = "Message",
                        onClick = { showSavedToast = true },
                        modifier = Modifier.weight(1f),
                    )
                    ConnectlySecondaryButton(
                        text = "Save Contact",
                        onClick = { showSavedToast = true },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp),
                    )
                }

                if (current.bio.isNotBlank()) {
                    SectionTitle("About")
                    Text(current.bio, style = LocalConnectlyTextStyles.current.bodyMd, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                if (current.expertiseTags.isNotEmpty()) {
                    SectionTitle("Expertise")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(current.expertiseTags) { tag -> TagChip(text = tag) }
                    }
                }

                if (current.metAtLabel.isNotBlank()) {
                    SectionTitle("Where you met")
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(current.metAtLabel, style = LocalConnectlyTextStyles.current.subheadingMd, color = MaterialTheme.colorScheme.onSurface)
                            Text(current.metDateLabel, style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                if (current.contextMemoryNote.isNotBlank()) {
                    SectionTitle("Context memory")
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            current.contextMemoryNote,
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(14.dp),
                        )
                    }
                }

                if (current.voiceMemoDurationSec > 0) {
                    SectionTitle("Voice memo")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
                            .clickable { showSavedToast = true }
                            .padding(14.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.Mic, contentDescription = "Play voice memo", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                        Text(
                            "${current.voiceMemoDurationSec}s memo from the event",
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 12.dp),
                        )
                    }
                }

                if (current.mutualsCount > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 8.dp),
                    ) {
                        Text("Mutual touchpoints", style = LocalConnectlyTextStyles.current.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            "${current.mutualsCount} Mutuals",
                            style = LocalConnectlyTextStyles.current.labelMd,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
                            .padding(14.dp),
                    ) {
                        Icon(Icons.Filled.Groups, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            current.mutualPreviewNames,
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                        Text(
                            "End-to-end encrypted offline vault",
                            style = LocalConnectlyTextStyles.current.labelSm,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 6.dp),
                        )
                    }
                }

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(24.dp))
            }
        }

        ToastPill(
            message = "Saved",
            visible = showSavedToast,
            onDismiss = { showSavedToast = false },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp),
        )
    }
}

@Composable
private fun ContactChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.small) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
            }
            Text(
                text,
                style = LocalConnectlyTextStyles.current.labelSm,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = if (icon != null) 4.dp else 0.dp),
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = LocalConnectlyTextStyles.current.headlineSm,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
    )
}
