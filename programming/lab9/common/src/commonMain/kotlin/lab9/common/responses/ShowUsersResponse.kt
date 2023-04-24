package lab9.common.responses

import kotlin.js.JsExport

@JsExport
data class ShowUsersResponse(
    val users: Array<ShowUserResponse>,
    val totalPages: Int,
    val totalElements: Int,
)