package com.connectly.app.data.repository

import com.connectly.app.data.model.Person
import com.connectly.app.data.model.Presence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface PeopleRepository {
    fun observePeople(): Flow<List<Person>>
    suspend fun getPerson(id: String): Person?
    suspend fun getAttendees(eventId: String): List<Person>
    fun scanTarget(): Person
}

// Static in-memory data standing in for a backend. Priya Patel's company was shown as
// both "CloudScale" and "CloudFlow" across different source mockups — "CloudScale" was
// kept as the single canonical value. Avatar photos are stable seeded picsum.photos
// placeholders rather than the source's internal Google-hosted mockup CDN URLs.
class MockPeopleRepository : PeopleRepository {

    private val people = listOf(
        Person(
            id = "rahul-sharma",
            name = "Rahul Sharma",
            role = "Founder & CEO",
            company = "XYZ Labs",
            location = "San Francisco, CA",
            avatarUrl = "https://picsum.photos/seed/rahul-sharma/200",
            isVerified = true,
            presence = Presence.ONLINE,
            linkedinHandle = "linkedin.com/in/rahulsharma",
            email = "rahul@xyzlabs.ai",
            twitterHandle = "@rahul_xyz",
            bio = "Building the future of autonomous agent infrastructure. Previously led AI platform teams at two unicorns before founding XYZ Labs.",
            expertiseTags = listOf("Artificial Intelligence", "Autonomous Agents", "Seed Investing", "Rust", "LLM Deployment"),
            metAtEventId = "google-ai-meetup",
            metAtLabel = "Google AI Meetup",
            metDateLabel = "22 Sep 2026",
            mutualsCount = 14,
            mutualPreviewNames = "Astrid, Ananya & 12 others",
            isFavorite = true,
            isSpeaker = true,
            isVip = true,
            contextMemoryNote = "Talked about his team's new agent-orchestration runtime and a possible Nexus Labs pilot.",
            voiceMemoDurationSec = 42,
            connectId = "CNX-94821",
        ),
        Person(
            id = "ananya-rao",
            name = "Ananya Rao",
            role = "ML Engineer",
            company = "Google",
            location = "Mountain View, CA",
            avatarUrl = "https://picsum.photos/seed/ananya-rao/200",
            isVerified = false,
            presence = Presence.ONLINE,
            linkedinHandle = "linkedin.com/in/ananyarao",
            email = "ananya.rao@example.com",
            bio = "ML engineer working on large-scale recommendation systems.",
            expertiseTags = listOf("Machine Learning", "Recommendation Systems", "TensorFlow"),
            metAtEventId = "google-ai-meetup",
            metAtLabel = "Google AI Meetup",
            metDateLabel = "22 Sep 2026",
            mutualsCount = 6,
            mutualPreviewNames = "Rahul & 5 others",
            connectId = "CNX-94822",
        ),
        Person(
            id = "karthik-r",
            name = "Karthik R",
            role = "Product Designer",
            company = "ABC",
            location = "Bengaluru, India",
            avatarUrl = "https://picsum.photos/seed/karthik-r/200",
            presence = Presence.AWAY,
            linkedinHandle = "linkedin.com/in/karthikr",
            bio = "Product designer focused on design systems and developer tooling.",
            expertiseTags = listOf("Design Systems", "Figma", "Developer Experience"),
            metAtEventId = "google-ai-meetup",
            metAtLabel = "Google AI Meetup",
            metDateLabel = "22 Sep 2026",
            mutualsCount = 3,
            mutualPreviewNames = "Rahul & 2 others",
            contextMemoryNote = "Shared his team's open-source design systems repo.",
            connectId = "CNX-94823",
        ),
        Person(
            id = "priya-patel",
            name = "Priya Patel",
            role = "VP Engineering",
            company = "CloudScale",
            location = "Seattle, WA",
            avatarUrl = "https://picsum.photos/seed/priya-patel/200",
            isVerified = true,
            presence = Presence.OFFLINE,
            linkedinHandle = "linkedin.com/in/priyapatel",
            bio = "Leading platform engineering at CloudScale. Passionate about developer productivity at scale.",
            expertiseTags = listOf("Platform Engineering", "Distributed Systems", "Kubernetes"),
            metAtEventId = "google-ai-meetup",
            metAtLabel = "Google AI Meetup",
            metDateLabel = "22 Sep 2026",
            mutualsCount = 8,
            mutualPreviewNames = "8 mutual connections",
            connectId = "CNX-94824",
        ),
        Person(
            id = "devon-chen",
            name = "Devon Chen",
            role = "Tech Lead, LLM Ops",
            company = "Matrix AI",
            location = "Austin, TX",
            avatarUrl = "https://picsum.photos/seed/devon-chen/200",
            presence = Presence.ONLINE,
            linkedinHandle = "linkedin.com/in/devonchen",
            isSpeaker = true,
            bio = "Tech lead for LLM operations at Matrix AI. Frequent speaker on production ML infrastructure.",
            expertiseTags = listOf("LLM Ops", "MLOps", "Infrastructure"),
            metAtEventId = "google-ai-meetup",
            metAtLabel = "Google AI Meetup",
            metDateLabel = "22 Sep 2026",
            mutualsCount = 5,
            mutualPreviewNames = "5 mutual connections",
            connectId = "CNX-94825",
        ),
        Person(
            id = "astrid-lindgren",
            name = "Astrid Lindgren",
            role = "Co-Founder",
            company = "Lumina Climate",
            location = "Stockholm, Sweden",
            avatarUrl = "https://picsum.photos/seed/astrid-lindgren/200",
            isVerified = true,
            presence = Presence.AWAY,
            linkedinHandle = "linkedin.com/in/astridlindgren",
            bio = "Co-founder of Lumina Climate, building carbon accounting tools for enterprise supply chains.",
            expertiseTags = listOf("Climate Tech", "Sustainability", "Enterprise SaaS"),
            metAtEventId = "slush-24",
            metAtLabel = "Slush '24",
            metDateLabel = "28 Nov 2024",
            mutualsCount = 4,
            mutualPreviewNames = "4 mutual connections",
            connectId = "CNX-94826",
        ),
    )

    private val scanTargetPerson = Person(
        id = "marcus-vance",
        name = "Marcus Vance",
        role = "VP Growth",
        company = "Nimbus Analytics",
        location = "San Francisco, CA",
        avatarUrl = "https://picsum.photos/seed/marcus-vance/200",
        isVerified = true,
        presence = Presence.ONLINE,
        linkedinHandle = "linkedin.com/in/marcusvance",
        bio = "Leads growth for Nimbus Analytics. Met via a live QR exchange.",
        expertiseTags = listOf("Growth", "Analytics", "Go-To-Market"),
        metAtEventId = null,
        metAtLabel = "QR Exchange",
        metDateLabel = "Just now",
        mutualsCount = 2,
        mutualPreviewNames = "2 mutual connections",
        connectId = "CNX-94827",
    )

    override fun observePeople(): Flow<List<Person>> = flowOf(people)

    override suspend fun getPerson(id: String): Person? =
        people.find { it.id == id } ?: if (id == scanTargetPerson.id) scanTargetPerson else null

    override suspend fun getAttendees(eventId: String): List<Person> =
        people.filter { it.metAtEventId == eventId }

    override fun scanTarget(): Person = scanTargetPerson
}
