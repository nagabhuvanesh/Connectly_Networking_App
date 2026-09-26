package com.connectly.app.feature.events

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.ChipOption
import com.connectly.app.core.components.ConnectlySearchField
import com.connectly.app.core.components.EventCard
import com.connectly.app.core.components.FilterChipRow
import com.connectly.app.core.theme.ConnectlyTextStyles
import com.connectly.app.data.repository.ServiceLocator

private const val ALL_CATEGORIES_KEY = "all"

@Composable
fun EventsScreen(onOpenEvent: (String) -> Unit) {
    val events by remember { ServiceLocator.eventsRepository.observeEvents() }.collectAsState(initial = emptyList())

    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ALL_CATEGORIES_KEY) }

    val categories = remember(events) { events.map { it.category }.distinct() }
    val chipOptions = remember(categories) {
        listOf(ChipOption(ALL_CATEGORIES_KEY, "All")) + categories.map { ChipOption(it, it) }
    }

    val filtered = events.filter { event ->
        val matchesQuery = query.isBlank() ||
            event.title.contains(query, ignoreCase = true) ||
            event.location.contains(query, ignoreCase = true)
        val matchesCategory = selectedCategory == ALL_CATEGORIES_KEY || event.category == selectedCategory
        matchesQuery && matchesCategory
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Events",
                    style = ConnectlyTextStyles.headlineLg,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                    Text(
                        "${events.size} Events",
                        style = ConnectlyTextStyles.labelSm,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
                Button(
                    onClick = { /* mock data: hosting flow is not implemented */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier.padding(start = 10.dp),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text("Host", style = ConnectlyTextStyles.labelMd, modifier = Modifier.padding(start = 4.dp))
                }
            }
            Text(
                "Everywhere you've shown up, and where you're headed next",
                style = ConnectlyTextStyles.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
            ConnectlySearchField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Search events by name, city, or topic",
                onFilterClick = { /* mock data: advanced filters are not implemented */ },
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        FilterChipRow(
            options = chipOptions,
            selectedKey = selectedCategory,
            onSelect = { selectedCategory = it },
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(filtered, key = { it.id }) { event ->
                EventCard(event = event, onClick = { onOpenEvent(event.id) })
            }
        }
    }
}
