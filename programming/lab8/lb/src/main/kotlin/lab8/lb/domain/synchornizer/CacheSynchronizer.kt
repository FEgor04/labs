package lab8.lb.domain.synchornizer

import lab8.entities.vehicle.Vehicle

interface CacheSynchronizer {
    suspend fun synchronize(): List<Vehicle>
}
