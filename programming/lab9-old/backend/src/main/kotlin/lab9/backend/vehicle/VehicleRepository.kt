package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : PagingAndSortingRepository<Vehicle, Int>, JpaRepository<Vehicle, Int> {
    fun findFirstById(id: Int): Vehicle?
}