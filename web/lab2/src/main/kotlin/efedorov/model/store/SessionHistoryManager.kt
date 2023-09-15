package efedorov.model.store

import efedorov.model.HistoryEntry
import jakarta.servlet.http.HttpSession
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class SessionHistoryManager : HistoryManager {
    override fun pushEntry(entry: HistoryEntry, session: HttpSession) {
        val currentEntries = getEntries(session).toMutableList()
        currentEntries.add(entry)
        session.setAttribute("history", currentEntries)
    }

    override fun getEntries(session: HttpSession): List<HistoryEntry> {
        val history = session.getAttribute("history") as List<HistoryEntry>?
        if (history.isNullOrEmpty()) {
            return emptyList()
        }
        return history
    }

    override fun getEntiresWithRadius(session: HttpSession, radius: Double): List<HistoryEntry> {
        return getEntries(session)
            .filter { it.point.radius == radius }
    }
}