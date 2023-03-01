package lab6.server.data.repositories

import lab6.shared.entities.CollectionInfo
import lab6.server.domain.repositories.VehicleRepository
import lab6.shared.entities.dtos.responses.ReplaceIfLowerResults
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType
import lab6.shared.logger.KCoolLoggerDelegate
import lab6.shared.logger.koolLogger
import java.io.File
import java.time.LocalDate
import java.util.TreeMap

class VehicleCsvRepository(
    private val file: File? = File("file.csv")
): VehicleRepository {
    private val map = TreeMap<Int, Vehicle>()
    private val creationDate = LocalDate.now()
    private val logger by KCoolLoggerDelegate()

    override fun add(veh: Vehicle): Int {
        val newId: Int
        if(map.isEmpty()) {
            newId = 1
        } else {
            newId = map.lastKey() + 1
        }
        val newVehicle = veh.copy(
            id = newId,
            creationDate = LocalDate.now()
        )
        newVehicle.validate()
        this.map[newId] = newVehicle
        return newId
    }

    override fun list(): List<Vehicle> {
        return map.toList().map { it.component2() }
    }

    override fun countByType(type: VehicleType?): Int {
        return this.map.filter { it.component2().type == type }.size
    }

    override fun clear() {
        this.map.clear()
    }

    override fun countLessThanEnginePower(power: Double): Int {
        val vehicles = this.map.filter { it.component2().enginePower < power }
        vehicles.forEach { this.map.remove(it.component1()) }
        return vehicles.size
    }

    override fun getCollectionInfo(): CollectionInfo {
        val date = this.creationDate
        return CollectionInfo(
            size= map.size,
            creationDate= date,
            type= map.javaClass.typeName
        )
    }

    override fun getMinById(): Vehicle? {
        if(map.size == 0) {
            return null
        }
        return this.map.firstEntry().component2()
    }

    override fun removeGreater(vehicle: Vehicle): Int {
        val vehicles = this.map.values.filter { it > vehicle }
        vehicles.forEach { this.map.remove(it.id) }
        return vehicles.size
    }

    override fun remove(id: Int) {
        this.map.remove(id)
    }

    override fun removeLower(vehicle: Vehicle): Int {
        val vehicles = this.map.values.filter { it < vehicle }
        vehicles.forEach { this.map.remove(it.id) }
        return vehicles.size
    }

    override fun replaceIfLower(id: Int, veh: Vehicle): ReplaceIfLowerResults {
        if(this.map.containsKey(id)) {
            val before = this.map.get(id)
            if(before != null && veh < before) {
                this.map[id] = veh.copy(id=id)
                return ReplaceIfLowerResults.REPLACED
            }
            return ReplaceIfLowerResults.NOT_REPLACED
        } else {
            return ReplaceIfLowerResults.NOT_EXISTS
        }
    }

    override fun updateVehicleById(newVehicle: Vehicle) {
        this.map[newVehicle.id] = newVehicle
    }

    override fun save() {
        if(file == null) {
            logger.warn("No file set. Saving cancelled")
            return
        }
        if(!file.exists()) {
            logger.warn("File $file does not exists. Saving cancelled.")
            return
        }
        if(!file.canWrite()) {
            logger.warn("Can not write to file. Saving cancelled.")
        }

        val outputStream = file.outputStream().writer()
        outputStream.use {
            for(veh in map.values) {
                it.write(veh.toCsv() + "\n")
            }
        }
        logger.info ("Successfully saved collection to $file")
    }

    /**
     * Парсит строку как csv.
     * Возвращает транспорт.
     *
     * @throws Exception если строка в плохом формате
     * TODO: нормальный эксепшн
     */
    private fun loadOneLine(line: String): Vehicle {
        val regex = Regex("(?<!\\\\),")
        val args = line.trim().split(regex, 0)
        if (args.size != 8) {
            throw Exception("Bad format of a file")
        }

        val id = args[0].toInt()
        val creationDate = java.time.LocalDate.parse(args[4])
        val vehicle = Vehicle.createVehicleFromString(
            name = args[1].trim(),
            xStr = args[2],
            yStr = args[3],
            vehicleType = args[6],
            fuelType = args[7],
            enginePowerStr = args[5]
        ).copy(id = id, creationDate = creationDate)
        vehicle.validate()
        return vehicle
    }

    override fun load() {
        if(file == null) {
            logger.warn("No file set. Loading cancelled")
            return
        }
        if(!file.exists()) {
            logger.warn("File $file does not exists. Loading cancelled.")
            return
        }
        if(!file.canRead()) {
            logger.warn("Can not read from file. Loading cancelled.")
        }

        val inputStream = file.inputStream().reader()
        inputStream.forEachLine {
            try {
                val veh = loadOneLine(it)
                this.map[veh.id] = veh
                logger.info ("Loaded vehicle $veh")
            }
            catch(e: Exception) {
                logger.error ("Could not load line $it. Error: $e")
            }
        }
        logger.info("Loaded all lines!")
    }
}