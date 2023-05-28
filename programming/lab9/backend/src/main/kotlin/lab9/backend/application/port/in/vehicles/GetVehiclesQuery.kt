package lab9.backend.application.port.`in`.vehicles

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import kotlinx.serialization.Serializable
import lab9.backend.domain.Vehicle
import java.time.LocalDate

data class GetVehiclesQuery(
    val pageNumber: Int,
    val pageSize: Int,
    val sorting: Sorting,
    val filter: Filter?,
) {
    data class Sorting(
        val field: Vehicle.Field,
        val asc: Boolean,
    )


    sealed class Filter {
        abstract val field: Vehicle.Field

        sealed class BetweenFilter <T: Comparable<T>>: Filter() {
            abstract val lowerBound: T?
            abstract val upperBound: T?
        }

        data class NumberFilter(
            override val lowerBound: Long?,
            override val upperBound: Long?,
            override val field: Vehicle.Field,
        ) : BetweenFilter<Long>()


        data class EnumFilter<T>(
            val values: List<T?>,
            override val field: Vehicle.Field,
        ) : Filter()

        data class PatternFilter(
            val pattern: String,
            override val field: Vehicle.Field
        ) : Filter()

        data class CreationDateFilter(
            override val lowerBound: LocalDate?,
            override val upperBound: LocalDate?,
            override val field: Vehicle.Field
        ): BetweenFilter<LocalDate>()
    }
}