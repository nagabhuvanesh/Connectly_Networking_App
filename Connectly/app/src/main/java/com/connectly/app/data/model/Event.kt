package com.connectly.app.data.model

enum class EventStatus { LIVE, UPCOMING, ATTENDED }

data class EventItem(
    val id: String,
    val title: String,
    val dateLabel: String,
    val location: String,
    val category: String,
    val attendeeCount: Int,
    val status: EventStatus,
    val coverColorHex: Long,
    val description: String = "",
)
