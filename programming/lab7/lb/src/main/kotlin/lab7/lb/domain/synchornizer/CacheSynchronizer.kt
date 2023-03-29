package lab7.lb.domain.synchornizer

import lab7.entities.vehicle.Vehicle

interface CacheSynchronizer {
    suspend fun synchronize(): List<Vehicle>
}