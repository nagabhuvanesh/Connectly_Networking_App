package com.connectly.app.feature.eventdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.ConnectlySearchField
import com.connectly.app.core.components.PersonCard
import com.connectly.app.core.components.QrCodeVisual
import com.connectly.app.core.components.SubScreenHeader
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.model.EventItem
import com.connectly.app.data.model.EventStatus
import com.connectly.app.data.model.Person
import com.connectly.app.data.repository.ServiceLocator

@Composable
private fun EventStatus.detailLabel(): String = when (this) {
    EventStatus.LIVE -> "Live Cohort"
    EventStatus.UPCOMING -> "RSVP Confirmed"
    EventStatus.ATTENDED -> "Attended"
}

@Composable
private fun EventStatus.detailColor() = when (this) {
    EventStatus.LIVE -> MaterialTheme.colorScheme.secondary
    EventStatus.UPCOMING -> MaterialTheme.colorScheme.primary
    EventStatus.ATTENDED -> MaterialTheme.colorScheme.onSurfaceVariant
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    eventId: String,
    onBack: () -> Unit,
    onOpenPerson: (String) -> Unit,
) {
    var event by remember { mutableStateOf<EventItem?>(null) }
    var attendees by remember { mutableStateOf<List<Person>>(emptyList()) }
    var showQrSheet by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(eventId) {
        event = ServiceLocator.eventsRepository.getEvent(eventId)
        attendees = ServiceLocator.peopleRepository.getAttendees(eventId)
    }

    val filteredAttendees = attendees.filter {
        query.isBlank() || it.name.contains(query, ignoreCase = true) || it.company.contains(query, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SubScreenHeader(
            title = event?.title.orEmpty(),
            onBack = onBack,
            trailingContent = {
                OutlinedButton(onClick = { showQrSheet = true }, shape = RoundedCornerShape(50)) {
                    Text("Event QR", style = LocalConnectlyTextStyles.current.labelMd)
                }
            },
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            event?.let { current ->
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = current.status.detailColor().copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            ) {
                                Box(modifier = Modifier.size(6.dp).background(current.status.detailColor(), CircleShape))
                                Text(
                                    current.status.detailLabel().uppercase(),
                                    style = LocalConnectlyTextStyles.current.labelSm,
                                    color = current.status.detailColor(),
                                    modifier = Modifier.padding(start = 6.dp),
                                )
                            }
                        }
                    }
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(current.coverColorHex), MaterialTheme.shapes.medium),
                        )
                        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                            Text(current.title, style = LocalConnectlyTextStyles.current.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                            Text(current.category, style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.primary)
                        }
                        Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            ) {
                                Text(
                                    "${current.attendeeCount}",
                                    style = LocalConnectlyTextStyles.current.headlineSm,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                )
                                Text(
                                    "PEOPLE",
                                    style = LocalConnectlyTextStyles.current.labelSm,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                )
                            }
                        }
                    }
                }
                item {
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        EventMetaRow(icon = Icons.Filled.CalendarToday, text = current.dateLabel)
                        EventMetaRow(icon = Icons.Filled.PinDrop, text = current.location, modifier = Modifier.padding(start = 16.dp))
                    }
                }
                if (current.description.isNotBlank()) {
                    item {
                        Text(
                            current.description,
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Text(
                            "Joined via Event QR",
                            style = LocalConnectlyTextStyles.current.labelMd,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp).weight(1f),
                        )
                        Text("Auto-synced", style = LocalConnectlyTextStyles.current.labelSm, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                item {
                    ConnectlySearchField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = "Search people in this event...",
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                ) {
                    Text(
                        "Attendees (${attendees.size})",
                        style = LocalConnectlyTextStyles.current.headlineSm,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                    )
                    Text("Recent", style = LocalConnectlyTextStyles.current.labelMd, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            items(filteredAttendees, key = { it.id }) { person ->
                PersonCard(
                    person = person,
                    onClick = { onOpenPerson(person.id) },
                    footerLabel = when {
                        person.mutualPreviewNames.isNotBlank() -> person.mutualPreviewNames
                        person.mutualsCount > 0 -> "${person.mutualsCount} mutual connections"
                        else -> null
                    },
                )
            }
        }
    }

    if (showQrSheet) {
        ModalBottomSheet(
            onDismissRequest = { showQrSheet = false },
            sheetState = rememberModalBottomSheetState(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    "Event check-in code",
                    style = LocalConnectlyTextStyles.current.headlineSm,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    event?.title.orEmpty(),
                    style = LocalConnectlyTextStyles.current.bodyMd,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 20.dp),
                )
                QrCodeVisual(seed = eventId)
            }
        }
    }
}

@Composable
private fun EventMetaRow(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
        Text(
            text,
            style = LocalConnectlyTextStyles.current.labelSm,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}
