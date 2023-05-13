package lab9.backend.adapter.`in`.web.vehicle

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import lab9.backend.adapter.`in`.web.dto.GetVehiclesFilterDTO
import lab9.backend.logger.KCoolLogger
import java.beans.PropertyEditorSupport

class GetVehiclesFilterDTOEditor : PropertyEditorSupport() {
    private val logger by KCoolLogger()
    private val serializer = Json { ignoreUnknownKeys = true }
    override fun setAsText(text: String) {
        value = if (text.isEmpty()) {
            null
        } else {
            try {
                val replacedText = text.replace("{AAAA{", "[")
                    .replace("}AAAA}", "]")
                logger.info("Replaced text is ${replacedText}")
                serializer.decodeFromString<GetVehiclesFilterDTO>(replacedText)
            }
            catch (e: Exception) {
                logger.error("Could not parse request: ${e}")
                null
            }
        }
    }

}