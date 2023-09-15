package efedorov.model.store

import efedorov.model.HistoryEntry
import jakarta.servlet.http.HttpSession

interface HistoryManager {
    fun pushEntry(entry: HistoryEntry, session: HttpSession)
    fun getEntries(session: HttpSession): List<HistoryEntry>
    fun getEntiresWithRadius(session: HttpSession, radius: Double): List<HistoryEntry>
}