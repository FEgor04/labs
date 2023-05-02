@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package lab9.common.requests

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class SignUpRequest(
    val username: String,
    val password: String,

) {
}
