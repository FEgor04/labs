package lab9.backend.adapter.out.persistence.vehicle

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.springframework.validation.annotation.Validated
import java.time.LocalDate

@Entity
@Table(name = "vehicles")
data class VehicleJpaEntity(
        @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,
        @Column(unique = false, nullable = false, name = "name")
    @NotBlank
    val name: String,
        @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    val creator: UserJpaEntity,
        @Min(-523)
    val x: Int,
        val y: Long?,
        @CreationTimestamp
    val creationDate: LocalDate,
        @Min(0)
    val enginePower: Double,
        val vehicleType: Vehicle.VehicleType,
        val fuelType: Vehicle.FuelType?,
) {

    fun toDomainEntity(): Vehicle {
        return if (id != null) {
            Vehicle.withID(
                Vehicle.VehicleID(id),
                name,
                User.UserID(creator.id!!),
                coordinates = Vehicle.Coordinates(x, y),
                creationDate,
                enginePower,
                vehicleType,
                fuelType
            )
        } else {
            Vehicle.withoutID(
                name,
                User.UserID(creator.id!!),
                coordinates = Vehicle.Coordinates(x, y),
                creationDate,
                enginePower,
                vehicleType,
                fuelType
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as VehicleJpaEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}