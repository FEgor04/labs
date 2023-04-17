package lab9.backend.entities

import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Min
import lab9.common.dto.CoordinatesDTO
import org.springframework.validation.annotation.Validated

@Embeddable
@Validated
data class Coordinates(
    @Access(AccessType.FIELD)
    @Min(-522)
    @Column(nullable = false)
    val x: Int,
    @Access(AccessType.FIELD)
    @Column(nullable = true)
    val y: Long?
) {
    fun toDTO(): CoordinatesDTO {
        return CoordinatesDTO(x, y)
    }

    companion object {
        fun fromDTO(dto: CoordinatesDTO): Coordinates {
            return Coordinates(dto.x, dto.y)
        }
    }
}