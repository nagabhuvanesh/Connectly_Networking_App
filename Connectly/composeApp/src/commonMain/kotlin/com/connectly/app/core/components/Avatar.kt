package com.connectly.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectly.app.data.model.Presence

@Composable
fun PresenceDotColor(presence: Presence): Color = when (presence) {
    Presence.ONLINE -> MaterialTheme.colorScheme.secondary
    Presence.AWAY -> MaterialTheme.colorScheme.tertiary
    Presence.OFFLINE -> MaterialTheme.colorScheme.outline
}

@Composable
fun Avatar(
    url: String,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceContainer),
    )
}

@Composable
fun AvatarWithPresence(
    url: String,
    presence: Presence,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Avatar(url = url, size = size)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(size * 0.28f)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .border(2.dp, MaterialTheme.colorScheme.surfaceContainerLowest, CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(size * 0.2f)
                    .clip(CircleShape)
                    .background(PresenceDotColor(presence)),
            )
        }
    }
}
