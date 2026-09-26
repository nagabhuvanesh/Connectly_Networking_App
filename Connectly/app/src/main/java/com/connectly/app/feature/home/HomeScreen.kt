package com.connectly.app.feature.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.EventCard
import com.connectly.app.core.components.PersonCard
import com.connectly.app.core.theme.ConnectlyTextStyles
import com.connectly.app.data.model.EventStatus
import com.connectly.app.data.repository.ServiceLocator

@Composable
fun HomeScreen(onOpenEvent: (String) -> Unit, onOpenPerson: (String) -> Unit) {
    val events by remember { ServiceLocator.eventsRepository.observeEvents() }.collectAsState(initial = emptyList())
    val people by remember { ServiceLocator.peopleRepository.observePeople() }.collectAsState(initial = emptyList())

    val pastEvents = events.filter { it.status != EventStatus.LIVE }
    val recentPeople = people.take(3)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                    Icon(
                        Icons.Filled.Event,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp),
                    )
                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text(
                            "Create New Event",
                            style = ConnectlyTextStyles.subheadingMd,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Text(
                            "Host, scan badges, and grow your network",
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                        )
                    }
                    Button(
                        onClick = { /* mock data: event creation is not wired to a backend */ },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text("Create", style = ConnectlyTextStyles.labelMd, modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }
        }

        item {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                    Box(modifier = Modifier.size(12.dp).background(MaterialTheme.colorScheme.secondary, CircleShape))
                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text(
                            "Active Momentum",
                            style = ConnectlyTextStyles.subheadingMd,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            "${recentPeople.size} new connections logged this week",
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small) {
                        Text(
                            "+18% sync",
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Past Events", style = ConnectlyTextStyles.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.padding(start = 8.dp),
                    ) {
                        Text(
                            "${pastEvents.size} total",
                            style = ConnectlyTextStyles.labelSm,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        )
                    }
                }
                Text(
                    "View all (${pastEvents.size})",
                    style = ConnectlyTextStyles.labelMd,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        items(pastEvents, key = { it.id }) { event ->
            EventCard(event = event, onClick = { onOpenEvent(event.id) })
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Text("People", style = ConnectlyTextStyles.headlineSm, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        "Recently connected",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text("Filter", style = ConnectlyTextStyles.labelMd, color = MaterialTheme.colorScheme.primary)
            }
        }
        items(recentPeople, key = { it.id }) { person ->
            PersonCard(person = person, onClick = { onOpenPerson(person.id) })
        }
    }
}
