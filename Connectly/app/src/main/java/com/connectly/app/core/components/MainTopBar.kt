package com.connectly.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectly.app.core.theme.ConnectlyTextStyles
import com.connectly.app.data.repository.ServiceLocator

// Persistent brand bar shown above every bottom-nav tab (logo + title + notifications +
// own avatar), matching the header chrome present across the source designs' 5 main tabs.
// Individual screens keep their own page-specific title/greeting row beneath this.
@Composable
fun MainTopBar(
    title: String,
    onAvatarClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val profile by ServiceLocator.profileRepository.profile.collectAsState()
    Column(modifier = modifier.statusBarsPadding()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Hub,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp),
                )
            }
            Text(
                title,
                style = ConnectlyTextStyles.headlineSm,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
            )
            IconButton(onClick = { /* no-op: mock data has no live notification feed */ }) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Avatar(
                url = profile.avatarUrl,
                size = 34.dp,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(onClick = onAvatarClick),
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}
