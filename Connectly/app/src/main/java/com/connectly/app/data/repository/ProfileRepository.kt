package com.connectly.app.data.repository

import com.connectly.app.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profile: StateFlow<UserProfile>
    fun updateProfile(profile: UserProfile)
}

class MockProfileRepository : ProfileRepository {
    private val _profile = MutableStateFlow(UserProfile())
    override val profile: StateFlow<UserProfile> = _profile

    override fun updateProfile(profile: UserProfile) {
        _profile.value = profile
    }
}

// Simple hand-rolled locator instead of a DI framework — kept intentionally small since
// this app has only a handful of repositories; swap for Hilt if the graph grows.
object ServiceLocator {
    val peopleRepository: PeopleRepository by lazy { MockPeopleRepository() }
    val eventsRepository: EventsRepository by lazy { MockEventsRepository() }
    val profileRepository: ProfileRepository by lazy { MockProfileRepository() }
}
