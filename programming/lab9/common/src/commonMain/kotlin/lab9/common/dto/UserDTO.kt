@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package lab9.common.dto

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class UserDTO(
    val id: Int,
    val username: String,
)