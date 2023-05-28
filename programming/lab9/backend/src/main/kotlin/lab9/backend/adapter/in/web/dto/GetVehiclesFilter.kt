package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.common.LocalDateSerializer
import lab9.backend.domain.Vehicle
import java.time.LocalDate

@Serializable
sealed class GetVehiclesFilterDTO {
    abstract val field: Vehicle.Field

    abstract fun toQueryFilter(): GetVehiclesQuery.Filter

    @Serializable
    data class NumberFilter(
        override val field: Vehicle.Field,
        val lowerBound: Long? = null,
        val upperBound: Long? = null,
    ) : GetVehiclesFilterDTO() {
        override fun toQueryFilter(): GetVehiclesQuery.Filter {
            return GetVehiclesQuery.Filter.NumberFilter(
                this.lowerBound,
                this.upperBound,
                this.field,
            )
        }
    }

    @Serializable
    data class PatternFilter(
        override val field: Vehicle.Field,
        val pattern: String,
    ) : GetVehiclesFilterDTO() {
        override fun toQueryFilter(): GetVehiclesQuery.Filter {
            return GetVehiclesQuery.Filter.PatternFilter(this.pattern, this.field)
        }
    }

    @Serializable
    data class VehicleTypeFilter(
        val values: List<Vehicle.VehicleType>
    ) : GetVehiclesFilterDTO() {
        override val field = Vehicle.Field.VEHICLE_TYPE

        override fun toQueryFilter(): GetVehiclesQuery.Filter {
            return GetVehiclesQuery.Filter.EnumFilter<Vehicle.VehicleType>(
                values = this.values,
                field = this.field
            )
        }
    }

    @Serializable
    data class FuelTypeFilter(
        val values: List<Vehicle.FuelType?> = emptyList(),
    ) : GetVehiclesFilterDTO(

    ) {
        override val field: Vehicle.Field = Vehicle.Field.FUEL_TYPE

        override fun toQueryFilter(): GetVehiclesQuery.Filter {
            return GetVehiclesQuery.Filter.EnumFilter<Vehicle.FuelType>(
                values, field
            )
        }
    }

    @Serializable
    data class DateFilter(
        @Serializable(LocalDateSerializer::class) val lowerBound: LocalDate? = null,
        @Serializable(LocalDateSerializer::class) val upperBound: LocalDate? = null,
    ) : GetVehiclesFilterDTO() {
        override val field = Vehicle.Field.CREATION_DATE
        override fun toQueryFilter(): GetVehiclesQuery.Filter {
            return GetVehiclesQuery.Filter.CreationDateFilter(lowerBound, upperBound, field)
        }
    }
}