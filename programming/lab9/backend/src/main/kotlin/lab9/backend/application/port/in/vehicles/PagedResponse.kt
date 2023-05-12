package lab9.backend.application.port.`in`.vehicles

import lab9.backend.domain.Vehicle

/**
 * @author fegor04
 */
data class PagedResponse<T>(
        val content: List<T>,
        val totalElements: Int,
        val totalPages: Int,
)
