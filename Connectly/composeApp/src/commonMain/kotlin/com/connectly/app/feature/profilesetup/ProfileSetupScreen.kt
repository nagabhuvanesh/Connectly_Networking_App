package com.connectly.app.feature.profilesetup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.connectly.app.core.components.ConnectlyPrimaryButton
import com.connectly.app.core.components.SubScreenHeader
import com.connectly.app.core.theme.LocalConnectlyTextStyles
import com.connectly.app.data.model.UserProfile
import com.connectly.app.data.repository.ServiceLocator

private val availableInterests = listOf(
    "Artificial Intelligence", "Product Strategy", "Venture Capital", "Developer Tools",
    "Climate Tech", "Design Systems", "Growth", "Autonomous Agents",
)

@Composable
fun ProfileSetupScreen(onDone: () -> Unit) {
    val defaults = remember { UserProfile() }
    var name by remember { mutableStateOf(defaults.name) }
    var role by remember { mutableStateOf(defaults.role) }
    var company by remember { mutableStateOf(defaults.company) }
    var location by remember { mutableStateOf(defaults.location) }
    var selectedInterests by remember { mutableStateOf(setOf("Artificial Intelligence", "Product Strategy")) }

    Column(modifier = Modifier.fillMaxSize()) {
        SubScreenHeader(title = "Profile Setup")
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Text(
                "Create your card",
                style = LocalConnectlyTextStyles.current.headlineLg,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                "This is what people see when you exchange details in person.",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp),
            )

            Box(modifier = Modifier.size(96.dp)) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add photo", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(14.dp)
                        .background(MaterialTheme.colorScheme.secondary, CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                )
            }
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(top = 10.dp),
            ) {
                Text(
                    "Ready to beam — profiles with a photo get 3x more recall",
                    style = LocalConnectlyTextStyles.current.labelSm,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }

            OutlinedButton(
                onClick = { /* mock data: LinkedIn import is not wired to a real provider */ },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF0A66C2), RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("in", style = LocalConnectlyTextStyles.current.labelSm, color = Color.White)
                }
                Text(
                    "Sync With LinkedIn",
                    style = LocalConnectlyTextStyles.current.subheadingMd,
                    modifier = Modifier.padding(start = 10.dp).weight(1f),
                    textAlign = TextAlign.Start,
                )
            }

            ProfileField(label = "Full name", value = name, onValueChange = { name = it })
            ProfileField(label = "Role / title", value = role, onValueChange = { role = it })
            ProfileField(label = "Company", value = company, onValueChange = { company = it })
            ProfileField(label = "Location", value = location, onValueChange = { location = it })

            Text(
                "Interests",
                style = LocalConnectlyTextStyles.current.subheadingMd,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )
            Text(
                "Pick a few topics so people know what to talk to you about.",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            InterestChipsFlow(
                interests = availableInterests,
                selected = selectedInterests,
                onToggle = { interest ->
                    selectedInterests = if (interest in selectedInterests) {
                        selectedInterests - interest
                    } else {
                        selectedInterests + interest
                    }
                },
                modifier = Modifier.padding(top = 12.dp, bottom = 20.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
                    .padding(14.dp),
            ) {
                Icon(Icons.Filled.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        "Offline Vault Protection",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        "Your card stays encrypted on-device until you choose to share it.",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Box(modifier = Modifier.padding(bottom = 24.dp))
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            ConnectlyPrimaryButton(
                text = "Finish Setup",
                onClick = {
                    ServiceLocator.profileRepository.updateProfile(
                        UserProfile(
                            name = name,
                            role = role,
                            company = company,
                            location = location,
                            interestTags = selectedInterests.toList(),
                        ),
                    )
                    onDone()
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                "By continuing, you agree to Connectly's Terms of Service and Privacy Policy.",
                style = LocalConnectlyTextStyles.current.labelSm,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            )
        }
    }
}

@Composable
private fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
    )
}

@Composable
private fun InterestChipsFlow(
    interests: List<String>,
    selected: Set<String>,
    onToggle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // A simple wrapping-free horizontal scroll list keeps this reusable without pulling
    // in an extra FlowRow dependency for a mock-data screen with a short, fixed tag list.
    LazyRow(modifier = modifier) {
        items(interests) { interest ->
            val isSelected = interest in selected
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
                    .clickable { onToggle(interest) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            ) {
                Text(
                    interest,
                    style = LocalConnectlyTextStyles.current.labelMd,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
