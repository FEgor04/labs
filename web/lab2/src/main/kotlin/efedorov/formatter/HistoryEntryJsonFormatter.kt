package efedorov.formatter

import efedorov.model.HistoryEntry
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class HistoryEntryJsonFormatter: Formatter<HistoryEntry> {
    override fun format(element: HistoryEntry): String {
        return Json.encodeToString(element)

        }
}