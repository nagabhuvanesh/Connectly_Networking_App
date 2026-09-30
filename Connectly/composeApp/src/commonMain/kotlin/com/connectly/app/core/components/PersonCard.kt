package com.connectly.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.model.Person

@Composable
fun PersonCard(
    person: Person,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    footerLabel: String? = person.metAtLabel.takeIf { it.isNotBlank() }?.let { "Met at $it • ${person.metDateLabel}" },
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarWithPresence(url = person.avatarUrl, presence = person.presence, size = 52.dp)
                Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            person.name,
                            style = LocalConnectlyTextStyles.current.subheadingMd,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        if (person.isVerified) {
                            Icon(
                                Icons.Filled.Verified,
                                contentDescription = "Verified",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            )
                        }
                    }
                    Text(
                        "${person.role} · ${person.company}",
                        style = LocalConnectlyTextStyles.current.bodyMd,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    if (person.linkedinHandle != null) {
                        Text(
                            person.linkedinHandle,
                            style = LocalConnectlyTextStyles.current.labelSm,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                    }
                }
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (footerLabel != null) {
                Spacer(modifier = Modifier.size(10.dp))
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(
                        footerLabel,
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    )
                }
            }
        }
    }
}
