package com.connectly.app.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val AUTH = "auth"
    const val PROFILE_SETUP = "profile_setup"
    const val MAIN = "main"

    // Nested inside MAIN's bottom-nav tabs
    const val HOME = "home"
    const val PEOPLE = "people"
    const val EVENTS = "events"
    const val SCANNER = "scanner"
    const val MY_PROFILE = "my_profile"

    // Pushed on top of MAIN
    const val EVENT_DETAILS = "event_details/{eventId}"
    const val PERSON_PROFILE = "person_profile/{personId}"

    fun eventDetails(eventId: String) = "event_details/$eventId"
    fun personProfile(personId: String) = "person_profile/$personId"
}
