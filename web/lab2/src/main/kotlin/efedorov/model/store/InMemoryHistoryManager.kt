package efedorov.model.store

import efedorov.model.HistoryEntry
import jakarta.servlet.http.HttpSession

class InMemoryHistoryManager : HistoryManager {
    private val map = HashMap<HttpSession, MutableList<HistoryEntry>>()

    override fun pushEntry(entry: HistoryEntry, session: HttpSession) {
        val array = map.getOrDefault(session, ArrayList())
        array.add(entry)
        map[session] = array
    }

    override fun getEntries(session: HttpSession): List<HistoryEntry> {
        return map[session] ?: ArrayList()
    }

    override fun getEntiresWithRadius(session: HttpSession, radius: Double): List<HistoryEntry> {
        return map
            .getOrDefault(session, arrayListOf())
            .filter { it.point.radius == radius }
    }
}