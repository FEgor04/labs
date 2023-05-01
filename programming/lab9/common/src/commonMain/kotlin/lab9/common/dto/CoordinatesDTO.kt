@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package lab9.common.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport()
@Serializable
data class CoordinatesDTO(
    val x: Int,
    val y: Long?,
) {
}
