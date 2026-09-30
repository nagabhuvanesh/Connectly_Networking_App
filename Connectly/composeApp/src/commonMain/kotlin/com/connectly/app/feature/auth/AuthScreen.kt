package com.connectly.app.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.connectly.app.core.theme.LocalConnectlyTextStyles

private enum class AuthMode { SIGN_IN, SIGN_UP }

@Composable
fun AuthScreen(onAuthenticated: () -> Unit) {
    var mode by remember { mutableStateOf(AuthMode.SIGN_IN) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }
    var keepSignedIn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    )
                    Text(
                        "Encrypted Node",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(start = 6.dp),
                    )
                }
            }
        }

        Box(modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally)) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Hub, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(32.dp))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(22.dp)
                    .background(MaterialTheme.colorScheme.secondary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(13.dp))
            }
        }

        Text(
            text = if (mode == AuthMode.SIGN_IN) "Welcome back" else "Get started with Connectly",
            style = LocalConnectlyTextStyles.current.headlineLg,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        )
        Text(
            text = if (mode == AuthMode.SIGN_IN) {
                "The fastest way to exchange credentials and remember who you met."
            } else {
                "Create your executive profile and start building a network that remembers."
            },
            style = LocalConnectlyTextStyles.current.bodyMd,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        )

        // Segmented Sign In / Create Account tab switcher.
        Box(
            modifier = Modifier
                .padding(top = 28.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(50)),
        ) {
            Row(modifier = Modifier.padding(4.dp)) {
                AuthMode.entries.forEach { entry ->
                    val selected = entry == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { mode = entry }
                            .background(
                                if (selected) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent,
                                RoundedCornerShape(50),
                            )
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (entry == AuthMode.SIGN_IN) "Sign In" else "Create Account",
                            style = LocalConnectlyTextStyles.current.labelMd,
                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(top = 20.dp)) {
            SocialLoginButton(
                label = "Continue with LinkedIn",
                badgeText = "in",
                badgeColor = Color(0xFF0A66C2),
                trailingLabel = "Recommended",
            )
            SocialLoginButton(
                label = "Continue with Google",
                badgeText = "G",
                badgeColor = Color(0xFF4285F4),
                modifier = Modifier.padding(top = 10.dp),
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 20.dp)) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
            Text(
                "OR WITH EMAIL",
                style = LocalConnectlyTextStyles.current.labelSm,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp),
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
        }

        Column(modifier = Modifier.padding(top = 20.dp)) {
            if (mode == AuthMode.SIGN_UP) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full name") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = authFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                )
            }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Work email") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = authFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
            )

            if (mode == AuthMode.SIGN_IN) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
                    Text(
                        "Forgot password?",
                        style = LocalConnectlyTextStyles.current.labelMd,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = null)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = MaterialTheme.shapes.medium,
                colors = authFieldColors(),
                modifier = Modifier.fillMaxWidth(),
            )

            if (mode == AuthMode.SIGN_IN) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = keepSignedIn, onCheckedChange = { keepSignedIn = it })
                        Text(
                            "Keep me signed in",
                            style = LocalConnectlyTextStyles.current.bodyMd,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.SelfImprovement,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            "Offline ready",
                            style = LocalConnectlyTextStyles.current.labelSm,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }
            }

            if (mode == AuthMode.SIGN_UP) {
                PasswordStrengthMeter(password = password, modifier = Modifier.padding(top = 10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Checkbox(checked = termsAccepted, onCheckedChange = { termsAccepted = it })
                    Text(
                        "I agree to the Terms of Service and Privacy Policy",
                        style = LocalConnectlyTextStyles.current.bodyMd,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                PerkBadge(modifier = Modifier.padding(top = 16.dp))
            }
        }

        Button(
            onClick = onAuthenticated,
            enabled = if (mode == AuthMode.SIGN_UP) termsAccepted else true,
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (mode == AuthMode.SIGN_IN) "Sign In to Connectly" else "Create Account",
                        style = LocalConnectlyTextStyles.current.subheadingMd,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp).size(18.dp),
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                    Icon(Icons.Filled.Security, contentDescription = null, modifier = Modifier.size(12.dp))
                    Text(
                        "Protected by Connectly Offline Vault",
                        style = LocalConnectlyTextStyles.current.labelSm,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        ) {
            Text(
                text = if (mode == AuthMode.SIGN_IN) "Don't have an account?" else "Already have an account?",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = if (mode == AuthMode.SIGN_IN) " Sign Up" else " Sign In",
                style = LocalConnectlyTextStyles.current.bodyMd,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    mode = if (mode == AuthMode.SIGN_IN) AuthMode.SIGN_UP else AuthMode.SIGN_IN
                },
            )
        }

        Text(
            "By continuing, you agree to Connectly's Terms of Service and Privacy Policy.",
            style = LocalConnectlyTextStyles.current.labelSm,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        )
    }
}

@Composable
private fun SocialLoginButton(
    label: String,
    badgeText: String,
    badgeColor: Color,
    modifier: Modifier = Modifier,
    trailingLabel: String? = null,
) {
    OutlinedButton(
        onClick = { /* mock data: social auth is not wired to a real provider */ },
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(badgeColor, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(badgeText, style = LocalConnectlyTextStyles.current.labelSm, color = Color.White)
        }
        Text(label, style = LocalConnectlyTextStyles.current.subheadingMd, modifier = Modifier.padding(start = 10.dp).weight(1f))
        if (trailingLabel != null) {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(50)) {
                Text(
                    trailingLabel,
                    style = LocalConnectlyTextStyles.current.labelSm,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }
        }
    }
}

@Composable
private fun authFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
)

@Composable
private fun PasswordStrengthMeter(password: String, modifier: Modifier = Modifier) {
    val strength = when {
        password.length >= 10 -> 3
        password.length >= 6 -> 2
        password.isNotEmpty() -> 1
        else -> 0
    }
    val label = when (strength) {
        3 -> "Strong"
        2 -> "Medium"
        1 -> "Weak"
        else -> "Enter a password"
    }
    val color = when (strength) {
        3 -> MaterialTheme.colorScheme.secondary
        2 -> MaterialTheme.colorScheme.tertiary
        1 -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outlineVariant
    }
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(4.dp)
                        .background(if (index < strength) color else MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape),
                )
            }
        }
        Text(label, style = LocalConnectlyTextStyles.current.labelSm, color = color, modifier = Modifier.padding(top = 6.dp))
    }
}

@Composable
private fun PerkBadge(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium)
            .padding(12.dp),
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
        Text(
            "Free executive tier included for your first 90 days",
            style = LocalConnectlyTextStyles.current.labelMd,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
