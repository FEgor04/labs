package lab9.backend.vehicle

import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import lab9.backend.entities.Coordinates
import lab9.backend.entities.Vehicle
import lab9.common.dto.VehicleColumn
import lab9.common.requests.VehicleFilter
import org.springframework.data.jpa.domain.Specification

object VehicleSpecifications {
    private fun convertNameToDatabaseColumn(column: VehicleColumn): String {
        return when (column) {
            VehicleColumn.ID -> "id"
            VehicleColumn.NAME -> "name"
            VehicleColumn.COORDINATES_X -> "x"
            VehicleColumn.COORDINATES_Y -> "y"
            VehicleColumn.CREATION_DATE -> "creationDate"
            VehicleColumn.ENGINE_POWER -> "enginePower"
            VehicleColumn.FUEL_TYPE -> "fuelType"
            VehicleColumn.VEHICLE_TYPE -> "vehicleType"
            VehicleColumn.CREATOR_ID -> "creatorId"
        }
    }

    private fun IntFilter(filter: VehicleFilter.IntFilter): Specification<Vehicle> {
        return Specification<Vehicle> { root, query, criteriaBuilder ->
            val columnName = convertNameToDatabaseColumn(filter.filterColumn)
            if (filter.lowerBound != null && filter.upperBound != null) {
                criteriaBuilder.between(
                    root.get<Vehicle>("coordinates").get("x"),
                    filter.lowerBound!!,
                    filter.upperBound!!
                )
            }
            if (filter.lowerBound != null && filter.upperBound == null) {
                criteriaBuilder.greaterThan(root.get(columnName), filter.lowerBound!!)
            }
            if (filter.lowerBound == null && filter.upperBound != null) {
                criteriaBuilder.lessThan(root.get(columnName), filter.upperBound!!)
            }
            criteriaBuilder.isNotNull(root.get<Vehicle>("id"))
        }
    }

    fun coordinatesFilter(filter: VehicleFilter.IntFilter): Specification<Vehicle> {
        return Specification<Vehicle> { root, query, criteriaBuilder ->
            val columnName = "x"
//            val columnName = convertNameToDatabaseColumn(filter.filterColumn)
            if (filter.lowerBound != null && filter.upperBound != null) {
                println("Sorting by coordinates!!!! ${filter.toString()}")
                criteriaBuilder.between(
                    root.get<Vehicle>("coordinates").get(columnName),
                    filter.lowerBound!!,
                    filter.upperBound!!
                )
            } else if (filter.lowerBound != null && filter.upperBound == null) {
                criteriaBuilder.greaterThan(
                    root.get<Vehicle>("coordinates").get(columnName),
                    filter.lowerBound!!
                )
            } else if (filter.lowerBound == null && filter.upperBound != null) {
                criteriaBuilder.lessThan(
                    root.get<Vehicle>("coordinates").get(columnName),
                    filter.upperBound!!
                )
            } else {
                criteriaBuilder.isNotNull(root.get<Vehicle>("id"))
            }
        }
    }

    fun FilterSpecification(filter: VehicleFilter): Specification<Vehicle> {
        when (filter) {
            is VehicleFilter.IntFilter -> {
                if (filter.filterColumn in listOf(VehicleColumn.COORDINATES_X, VehicleColumn.COORDINATES_Y)) {
                    return coordinatesFilter(filter)
                }
                return IntFilter(filter)
            }
        }
    }
}
