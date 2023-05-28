package lab9.backend.adapter.`in`.web.dto

/**
 * @author fegor04
 */
data class GetUsersResponse(
        val totalElements: Int,
        val totalPages: Int,
        val content: List<ShowUserResponse>,
)