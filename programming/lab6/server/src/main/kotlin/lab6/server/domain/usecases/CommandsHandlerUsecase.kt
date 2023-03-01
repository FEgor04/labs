package lab6.server.domain.usecases

import lab6.shared.entities.CollectionInfo
import lab6.server.domain.repositories.VehicleRepository
import lab6.shared.entities.dtos.responses.ReplaceIfLowerResults
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType

class CommandsHandlerUseCase(
    private val repository: VehicleRepository
) {
    val collectionInfo: CollectionInfo
        get() {
            return repository.getCollectionInfo()
        }

    fun add(vehicle: Vehicle): Int {
        return repository.add(vehicle)
    }

    fun list(): List<Vehicle> {
        return repository.list()
    }

    fun countByType(type: VehicleType?): Int {
        return repository.countByType(type)
    }

    fun clear() {
        return repository.clear()
    }

    fun countLessThanEnginePower(power: Double): Int {
        return repository.countLessThanEnginePower(power)
    }

    fun getMinById(): Vehicle? {
        return repository.getMinById()
    }

    fun removeGreater(veh: Vehicle): Int {
        return repository.removeGreater(veh)
    }

    fun remove(id: Int) {
        return repository.remove(id)
    }

    fun replaceIfLower(id: Int, veh: Vehicle): ReplaceIfLowerResults {
        return repository.replaceIfLower(id, veh)
    }

    fun update(id: Int, veh: Vehicle) {
        return repository.updateVehicleById(veh.copy(id=id))
    }

    fun save() {
        return repository.save()
    }
}