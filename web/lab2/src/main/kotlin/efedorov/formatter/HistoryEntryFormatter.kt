package efedorov.formatter

import efedorov.model.HistoryEntry
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class HistoryEntryFormatter : Formatter<HistoryEntry> {
    override fun format(element: HistoryEntry): String {
        val colorFormat = if (element.success) {
            "text-lime-600"
        } else {
            "text-red-600"
        }
        val tdClasses = "border border-slate-200 border-collapse p-1 $colorFormat";
        val dateTimeFormatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withZone(ZoneId.systemDefault())
            .withLocale(Locale("RU"))

        return """
            <tr>
                <td class="$tdClasses">
                    ${element.point.x}
                </td>
                <td class="$tdClasses">
                    ${element.point.y}
                </td>
                <td class="$tdClasses">
                    ${element.point.radius}
                </td>
                <td class="$tdClasses">
                    ${
            if (element.success) {
                "Да!"
            } else {
                "Нет("
            }
        }
                </td>
                <td class="$tdClasses">
                    ${element.executionTime.toNanos()} ns
                </td>
                <td class="$tdClasses">
                    ${dateTimeFormatter.format(element.serverTime)}
                </td>
            </tr>
        """.trimIndent()
    }
}