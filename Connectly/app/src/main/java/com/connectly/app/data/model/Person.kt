package com.connectly.app.data.model

enum class Presence { ONLINE, AWAY, OFFLINE }

data class Person(
    val id: String,
    val name: String,
    val role: String,
    val company: String,
    val location: String = "",
    val avatarUrl: String,
    val isVerified: Boolean = false,
    val presence: Presence = Presence.OFFLINE,
    val linkedinHandle: String? = null,
    val email: String? = null,
    val twitterHandle: String? = null,
    val bio: String = "",
    val expertiseTags: List<String> = emptyList(),
    val metAtEventId: String? = null,
    val metAtLabel: String = "",
    val metDateLabel: String = "",
    val mutualsCount: Int = 0,
    val mutualPreviewNames: String = "",
    val isFavorite: Boolean = false,
    val isSpeaker: Boolean = false,
    val isVip: Boolean = false,
    val contextMemoryNote: String = "",
    val voiceMemoDurationSec: Int = 0,
    val connectId: String = "",
)
