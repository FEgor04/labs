package lab9.backend.adapter.out.persistence.vehicle

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : PagingAndSortingRepository<VehicleJpaEntity, Int>, JpaRepository<VehicleJpaEntity, Int>, JpaSpecificationExecutor<VehicleJpaEntity> {
    fun findFirstById(id: Int): VehicleJpaEntity?
    fun findAllByIdBetween(p: Pageable, lowerBound: Int, upperBound: Int) : Page<VehicleJpaEntity>
}