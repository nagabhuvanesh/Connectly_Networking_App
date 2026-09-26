package com.connectly.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.connectly.app.core.components.BottomNavDestination
import com.connectly.app.core.components.ConnectlyBottomNavBar
import com.connectly.app.core.components.MainTopBar

@Composable
fun MainScaffold(
    navController: NavHostController,
    current: BottomNavDestination,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            val title = when (current) {
                BottomNavDestination.Home -> "Home Feed"
                BottomNavDestination.People -> "People"
                BottomNavDestination.Events -> "Connectly Events"
                BottomNavDestination.Scan -> "QR Scanner"
                BottomNavDestination.Profile -> "User Profile"
            }
            MainTopBar(
                title = title,
                onAvatarClick = {
                    if (current != BottomNavDestination.Profile) {
                        navigateToTab(navController, Routes.MY_PROFILE)
                    }
                },
            )
        },
        bottomBar = {
            ConnectlyBottomNavBar(
                current = current,
                onSelect = { destination ->
                    if (destination != current) {
                        val route = when (destination) {
                            BottomNavDestination.Home -> Routes.HOME
                            BottomNavDestination.People -> Routes.PEOPLE
                            BottomNavDestination.Scan -> Routes.SCANNER
                            BottomNavDestination.Events -> Routes.EVENTS
                            BottomNavDestination.Profile -> Routes.MY_PROFILE
                        }
                        navigateToTab(navController, route)
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize(),
        ) {
            content()
        }
    }
}
