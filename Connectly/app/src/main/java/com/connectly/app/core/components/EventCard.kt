package com.connectly.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectly.app.core.theme.ConnectlyTextStyles
import com.connectly.app.data.model.EventItem
import com.connectly.app.data.model.EventStatus

@Composable
private fun EventStatus.label(): String = when (this) {
    EventStatus.LIVE -> "Happening Tonight"
    EventStatus.UPCOMING -> "RSVP Confirmed"
    EventStatus.ATTENDED -> "Attended"
}

@Composable
private fun EventStatus.color() = when (this) {
    EventStatus.LIVE -> MaterialTheme.colorScheme.secondary
    EventStatus.UPCOMING -> MaterialTheme.colorScheme.primary
    EventStatus.ATTENDED -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Composable
fun EventCard(
    event: EventItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                Surface(color = event.status.color().copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Box(modifier = Modifier.size(6.dp).background(event.status.color(), CircleShape))
                        Text(
                            event.status.label(),
                            style = ConnectlyTextStyles.labelSm,
                            color = event.status.color(),
                            modifier = Modifier.padding(start = 6.dp),
                        )
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    Text(
                        event.category,
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(event.coverColorHex), MaterialTheme.shapes.small),
                )
                Text(
                    event.title,
                    style = ConnectlyTextStyles.subheadingMd,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 12.dp).weight(1f),
                )
            }
            Column(modifier = Modifier.padding(top = 10.dp)) {
                MetaChip(icon = Icons.Filled.CalendarToday, text = event.dateLabel)
                MetaChip(icon = Icons.Filled.PinDrop, text = event.location, modifier = Modifier.padding(top = 6.dp))
            }
            HorizontalDivider(modifier = Modifier.padding(top = 12.dp, bottom = 10.dp), color = MaterialTheme.colorScheme.outlineVariant)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Groups, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                Text(
                    "${event.attendeeCount} attending",
                    style = ConnectlyTextStyles.labelSm,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 6.dp).weight(1f),
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
private fun MetaChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
        Text(
            text,
            style = ConnectlyTextStyles.labelSm,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}
