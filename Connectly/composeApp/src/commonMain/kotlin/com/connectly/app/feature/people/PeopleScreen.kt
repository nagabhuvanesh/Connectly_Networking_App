package com.connectly.app.feature.people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.ChipOption
import com.connectly.app.core.components.ConnectlySearchField
import com.connectly.app.core.components.FilterChipRow
import com.connectly.app.core.components.PersonCard
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.repository.ServiceLocator

private const val ALL_EVENTS_KEY = "all"

@Composable
fun PeopleScreen(onOpenPerson: (String) -> Unit) {
    val people by remember { ServiceLocator.peopleRepository.observePeople() }.collectAsState(initial = emptyList())
    val events by remember { ServiceLocator.eventsRepository.observeEvents() }.collectAsState(initial = emptyList())

    var query by remember { mutableStateOf("") }
    var selectedEventId by remember { mutableStateOf(ALL_EVENTS_KEY) }

    val filtered = people.filter { person ->
        val matchesQuery = query.isBlank() ||
            person.name.contains(query, ignoreCase = true) ||
            person.company.contains(query, ignoreCase = true) ||
            person.role.contains(query, ignoreCase = true)
        val matchesEvent = selectedEventId == ALL_EVENTS_KEY || person.metAtEventId == selectedEventId
        matchesQuery && matchesEvent
    }

    val chipOptions = remember(events) {
        listOf(ChipOption(ALL_EVENTS_KEY, "All events")) + events.map { ChipOption(it.id, it.title) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("People", style = LocalConnectlyTextStyles.current.headlineLg, color = MaterialTheme.colorScheme.onSurface)
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.padding(start = 10.dp),
                ) {
                    Text(
                        "${people.size} Connections",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
            Text(
                "Everyone you've met across events",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
            ConnectlySearchField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Search by name, role, or company",
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        FilterChipRow(
            options = chipOptions,
            selectedKey = selectedEventId,
            onSelect = { selectedEventId = it },
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(14.dp)) {
                Icon(Icons.Filled.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
                    Text(
                        "Offline Vault Active",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                    Text(
                        "${people.size} contacts securely synchronized",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
                Text(
                    "100% Synced",
                    style = LocalConnectlyTextStyles.current.labelSm,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(filtered, key = { it.id }) { person ->
                PersonCard(person = person, onClick = { onOpenPerson(person.id) })
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                ) {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        "Connections are automatically backed up and synced via Event & Profile QR scans.",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}
