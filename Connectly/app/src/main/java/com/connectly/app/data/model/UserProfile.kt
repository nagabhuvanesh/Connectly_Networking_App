package com.connectly.app.data.model

// The single canonical "current user" identity used everywhere in the app (Home greeting,
// Profile tab, My QR, and the Scanner's My-QR sheet). The source mockups used three
// different placeholder names across different screens (Karan Mehta / Bhuvanesh Kovuri /
// Alex Rivera) — Karan Mehta was chosen as canonical since that is the identity the mockups
// show being entered during onboarding's Profile Setup step, which is the natural
// "source of truth" for who the signed-in user is.
data class UserProfile(
    val id: String = "me",
    val name: String = "Karan Mehta",
    val role: String = "Senior Product Lead",
    val focusArea: String = "AI Platforms",
    val company: String = "Nexus Labs",
    val location: String = "San Francisco, CA",
    val avatarUrl: String = "https://picsum.photos/seed/karan-mehta/200",
    val linkedinHandle: String = "linkedin.com/in/karanmehta",
    val connectId: String = "CNX-10293",
    val interestTags: List<String> = listOf("Artificial Intelligence", "Product Strategy", "Venture Capital", "Developer Tools"),
)
