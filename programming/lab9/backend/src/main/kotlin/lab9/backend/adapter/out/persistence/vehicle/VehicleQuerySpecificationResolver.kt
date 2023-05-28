package lab9.backend.adapter.out.persistence.vehicle

import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.Comparator

@Component
class VehicleQuerySpecificationResolver(
    private val vehicleFieldResolver: VehicleFieldResolver,
) {
    fun resolve(getVehiclesQuery: GetVehiclesQuery): Specification<VehicleJpaEntity> {
        return when (getVehiclesQuery.filter) {
            is GetVehiclesQuery.Filter.EnumFilter<*> -> resolveEnumFilter(getVehiclesQuery.filter)
            is GetVehiclesQuery.Filter.PatternFilter -> resolvePatternFilter(getVehiclesQuery.filter)
            is GetVehiclesQuery.Filter.BetweenFilter<*> -> resolveBetweenFilter(getVehiclesQuery.filter)
            null -> resolveAnyFilter()
        }
    }

    private fun resolveNumberFilter(
        filter: GetVehiclesQuery.Filter.NumberFilter,
    ): Specification<VehicleJpaEntity> {
        return Specification<VehicleJpaEntity> { root, query, criteriaBuilder ->
            if (filter.lowerBound == null && filter.upperBound != null) {
                return@Specification criteriaBuilder.lessThanOrEqualTo(
                    root.get(vehicleFieldResolver.toPathName(filter.field)),
                    filter.upperBound
                )
            }
            if (filter.lowerBound != null && filter.upperBound == null) {
                return@Specification criteriaBuilder.greaterThanOrEqualTo(
                    root.get(vehicleFieldResolver.toPathName(filter.field)),
                    filter.lowerBound
                )
            }
            if (filter.lowerBound != null && filter.upperBound != null) {
                return@Specification criteriaBuilder.between(
                    root.get(vehicleFieldResolver.toPathName(filter.field)),
                    filter.lowerBound,
                    filter.upperBound
                )
            }
            return@Specification criteriaBuilder.and()
        }
    }

    fun <T: Comparable<T>> resolveBetweenFilter(
        filter: GetVehiclesQuery.Filter.BetweenFilter<T>,
    ): Specification<VehicleJpaEntity> {
        return Specification<VehicleJpaEntity> { root, query, criteriaBuilder ->
            val test = root.get<T>(vehicleFieldResolver.toPathName(filter.field))
            if (filter.lowerBound == null && filter.upperBound != null) {
                return@Specification criteriaBuilder.lessThanOrEqualTo(
                    test,
                    filter.upperBound!!,
                )
            }
            if (filter.lowerBound != null && filter.upperBound == null) {
                return@Specification criteriaBuilder.greaterThanOrEqualTo(
                    test,
                    filter.lowerBound!!,
                )
            }
            if (filter.lowerBound != null && filter.upperBound != null) {
                return@Specification criteriaBuilder.between(
                    test,
                    filter.lowerBound!!,
                    filter.upperBound!!,
                )
            }
            return@Specification criteriaBuilder.and()
        }
    }

    private fun resolvePatternFilter(filter: GetVehiclesQuery.Filter.PatternFilter): Specification<VehicleJpaEntity> {
        return Specification<VehicleJpaEntity> { root, _, criteriaBuilder ->
            criteriaBuilder.like(
                root.get<String>(vehicleFieldResolver.toPathName(filter.field)),
                "%" + filter.pattern + "%"
            )
        }
    }

    private fun <T> resolveEnumFilter(
        filter: GetVehiclesQuery.Filter.EnumFilter<T>,
    ): Specification<VehicleJpaEntity> {
        return Specification<VehicleJpaEntity> { root, _, criteriaBuilder ->
            val inEnumPredicate: Predicate = criteriaBuilder.isTrue(
                root
                    .get<String>(vehicleFieldResolver.toPathName(filter.field))
                    .`in`(filter.values.map { it })
            )
            val isNullPredicate: Predicate = criteriaBuilder.isNull(
                root.get<String>(vehicleFieldResolver.toPathName(filter.field))
            )

            if (null in filter.values) {
                criteriaBuilder.or(
                    inEnumPredicate,
                    isNullPredicate,
                )
            } else {
                inEnumPredicate
            }
        }
    }

    private fun resolveAnyFilter(): Specification<VehicleJpaEntity> {
        return Specification<VehicleJpaEntity> { r, q, cb ->
            cb.and()
        }
    }
}