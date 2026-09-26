package com.connectly.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.connectly.app.feature.auth.AuthScreen
import com.connectly.app.feature.eventdetails.EventDetailsScreen
import com.connectly.app.feature.events.EventsScreen
import com.connectly.app.feature.home.HomeScreen
import com.connectly.app.feature.myprofile.MyProfileScreen
import com.connectly.app.feature.onboarding.OnboardingScreen
import com.connectly.app.feature.people.PeopleScreen
import com.connectly.app.feature.personprofile.PersonProfileScreen
import com.connectly.app.feature.profilesetup.ProfileSetupScreen
import com.connectly.app.feature.scanner.ScannerScreen
import com.connectly.app.feature.splash.SplashScreen

// A single root NavHost, rather than a nested tab-graph, keeps the bottom-nav show/hide
// logic simple: the 5 tab routes render inside MainScaffold (bottom bar visible), while
// pushed detail routes (event details, person profile) render full-screen with their own
// SubScreenHeader and no bottom bar, matching the source designs.
@Composable
fun ConnectlyNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = {
                navController.navigate(Routes.ONBOARDING) { popUpTo(Routes.SPLASH) { inclusive = true } }
            })
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Routes.AUTH) { popUpTo(Routes.ONBOARDING) { inclusive = true } }
                },
                onSkip = {
                    navController.navigate(Routes.AUTH) { popUpTo(Routes.ONBOARDING) { inclusive = true } }
                },
            )
        }
        composable(Routes.AUTH) {
            AuthScreen(onAuthenticated = {
                navController.navigate(Routes.PROFILE_SETUP) { popUpTo(Routes.AUTH) { inclusive = true } }
            })
        }
        composable(Routes.PROFILE_SETUP) {
            ProfileSetupScreen(onDone = {
                navController.navigate(Routes.HOME) { popUpTo(Routes.PROFILE_SETUP) { inclusive = true } }
            })
        }

        composable(Routes.HOME) {
            MainScaffold(navController = navController, current = com.connectly.app.core.components.BottomNavDestination.Home) {
                HomeScreen(
                    onOpenEvent = { navController.navigate(Routes.eventDetails(it)) },
                    onOpenPerson = { navController.navigate(Routes.personProfile(it)) },
                )
            }
        }
        composable(Routes.PEOPLE) {
            MainScaffold(navController = navController, current = com.connectly.app.core.components.BottomNavDestination.People) {
                PeopleScreen(onOpenPerson = { navController.navigate(Routes.personProfile(it)) })
            }
        }
        composable(Routes.EVENTS) {
            MainScaffold(navController = navController, current = com.connectly.app.core.components.BottomNavDestination.Events) {
                EventsScreen(onOpenEvent = { navController.navigate(Routes.eventDetails(it)) })
            }
        }
        composable(Routes.SCANNER) {
            MainScaffold(navController = navController, current = com.connectly.app.core.components.BottomNavDestination.Scan) {
                ScannerScreen(onConnected = { navController.navigate(Routes.personProfile(it)) })
            }
        }
        composable(Routes.MY_PROFILE) {
            MainScaffold(navController = navController, current = com.connectly.app.core.components.BottomNavDestination.Profile) {
                MyProfileScreen()
            }
        }

        composable(
            route = Routes.EVENT_DETAILS,
            arguments = listOf(navArgument("eventId") { }),
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId").orEmpty()
            EventDetailsScreen(
                eventId = eventId,
                onBack = { navController.popBackStack() },
                onOpenPerson = { navController.navigate(Routes.personProfile(it)) },
            )
        }
        composable(
            route = Routes.PERSON_PROFILE,
            arguments = listOf(navArgument("personId") { }),
        ) { backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId").orEmpty()
            PersonProfileScreen(
                personId = personId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

fun navigateToTab(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
