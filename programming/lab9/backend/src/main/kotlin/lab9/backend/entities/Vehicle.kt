package lab9.backend.entities

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kotlinx.serialization.Serializable
import lab9.backend.utils.LocalDateSerializer
import lab9.common.dto.VehicleDTO
import lab9.common.responses.ShowVehicleResponse
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Serializable
data class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,
    @Column(unique = true, nullable = false, name = "name")
    val name: String,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    val creator: User?,
    @Embedded
    val coordinates: Coordinates,
    @CreationTimestamp
    @Serializable(LocalDateSerializer::class)
    val creationDate: LocalDate,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?,
) {
    fun toDTO(): VehicleDTO {
        return VehicleDTO(
            id = this.id,
            name = name,
            coordinates = this.coordinates.toDTO(),
            creationDate = this.creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            enginePower = this.enginePower,
            vehicleType = this.vehicleType,
            fuelType = this.fuelType,
        )
    }

    fun toShowVehicleResponse(): ShowVehicleResponse {
        return ShowVehicleResponse(
            id = this.id,
            name = name,
            coordinates = this.coordinates.toDTO(),
            creationDate = this.creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            enginePower = this.enginePower,
            vehicleType = this.vehicleType,
            fuelType = this.fuelType,
            creatorId = this.creator!!.id
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Vehicle

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }

}