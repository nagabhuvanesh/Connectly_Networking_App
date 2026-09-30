package com.connectly.app.data.repository

import com.connectly.app.data.model.EventItem
import com.connectly.app.data.model.EventStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface EventsRepository {
    fun observeEvents(): Flow<List<EventItem>>
    suspend fun getEvent(id: String): EventItem?
}

// Static in-memory data standing in for a backend until a real API is wired up.
// Event/location details are normalized against conflicting copies across the source
// mockups (e.g. "Google AI Meetup" was shown as both Bangalore and Moscone West SF —
// Moscone West, San Francisco was kept as the single canonical location).
class MockEventsRepository : EventsRepository {

    private val events = listOf(
        EventItem(
            id = "google-ai-meetup",
            title = "Google AI Meetup",
            dateLabel = "22 Sep 2026",
            location = "Moscone West, San Francisco",
            category = "Tech & AI",
            attendeeCount = 87,
            status = EventStatus.LIVE,
            coverColorHex = 0xFF2A14B4,
            description = "An executive gathering of AI platform builders, researchers, and founders shaping the next generation of intelligent products.",
        ),
        EventItem(
            id = "startup-connect",
            title = "Startup Connect",
            dateLabel = "14 Aug 2026",
            location = "WeWork SoMa, San Francisco",
            category = "Founders & Angels",
            attendeeCount = 8,
            status = EventStatus.ATTENDED,
            coverColorHex = 0xFF006C49,
            description = "A curated mixer connecting early-stage founders with operators and angel investors.",
        ),
        EventItem(
            id = "slush-24",
            title = "Slush '24",
            dateLabel = "28 Nov 2024",
            location = "Messukeskus, Helsinki",
            category = "Networking",
            attendeeCount = 14,
            status = EventStatus.ATTENDED,
            coverColorHex = 0xFF692400,
            description = "One of the world's leading startup and tech events, bringing together founders, investors and operators.",
        ),
        EventItem(
            id = "tech-disrupt-summit-25",
            title = "Tech Disrupt Summit '25",
            dateLabel = "10 Mar 2025",
            location = "Javits Center, New York",
            category = "Tech & AI",
            attendeeCount = 42,
            status = EventStatus.UPCOMING,
            coverColorHex = 0xFF4338CA,
            description = "A summit spotlighting disruptive technology companies and the executives building them.",
        ),
    )

    override fun observeEvents(): Flow<List<EventItem>> = flowOf(events)

    override suspend fun getEvent(id: String): EventItem? = events.find { it.id == id }
}
