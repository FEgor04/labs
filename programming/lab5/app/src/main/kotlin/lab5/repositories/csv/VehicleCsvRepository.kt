package lab5.repositories.csv

import lab5.entities.vehicle.Vehicle
import lab5.entities.vehicle.VehicleComparator
import lab5.entities.vehicle.VehicleFactory
import lab5.entities.vehicle.VehicleType
import lab5.repositories.CollectionInfo
import lab5.repositories.ReplaceIfLowerResults
import lab5.repositories.VehicleRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.TreeMap

/**
 * Класс реализации Repository с хранением в CSV
 * @property filename путь к csv файлу
 * @property creationDate дата создания коллекции (создания инстанса)
 * @property map сама коллекция типа java.util.TreeMap
 */
class VehicleCsvRepository(private val filename: String?): VehicleRepository {
    private val creationDate = java.time.LocalDateTime.now()
    private var map = java.util.TreeMap<Int, Vehicle>()

    override fun insertVehicle(vehicle: Vehicle) {
        val newId = if (this.map.size == 0) {
            1
        } else {
            this.map.lastKey() + 1
        }
        val newVehicle = vehicle.copy(id = newId, creationDate = java.time.LocalDate.now())
        newVehicle.validate()
        this.map[newVehicle.id] = newVehicle
    }

    override fun getVehicleById(id: Int): Vehicle? {
        return this.map[id]
    }

    override fun updateVehicleById(vehicle: Vehicle) {
        removeVehicle(vehicle.id)
        this.map[vehicle.id] = vehicle
    }

    override fun listAllVehicles(): Iterable<Vehicle> {
        return this.map.values
    }

    override fun removeVehicle(id: Int) {
        this.map.remove(id)
    }

    override fun saveCollection() {
        if(filename.isNullOrEmpty()) {
            return
        }

        val file = OutputStreamWriter(FileOutputStream(File(filename)))
        file.use {
            for (item in listAllVehicles()) {
                file.write(item.toCsv() + "\n")
            }
            file.flush()
        }
    }

    override fun getCollectionInfo(): CollectionInfo {
        return CollectionInfo(
            type = this.map.javaClass.typeName,
            elementsCount = this.map.size,
            initDate = creationDate
        )
    }

    override fun loadData() {
        if(filename.isNullOrEmpty()) {
            return
        }

        val file = InputStreamReader(FileInputStream(File(filename)))
        file.use { file ->
            for (line in file.readLines()) {
                loadOneLine(line)
            }
        }
    }

    override fun removeGreater(veh: Vehicle) {
        map.filter { VehicleComparator().compare(it.component2(), veh) > 0 }.forEach { map.remove(it.component1()) }
    }

    override fun removeLower(veh: Vehicle) {
        map.filter { VehicleComparator().compare(it.component2(), veh) < 0 }.forEach { map.remove(it.component1()) }
    }

    override fun replaceIfLower(id: Int, vehicle: Vehicle): ReplaceIfLowerResults {
        val element = getVehicleById(id)
        if(element == null) {
            return ReplaceIfLowerResults.NOT_EXISTS
        }
        if(VehicleComparator().compare(vehicle, element) < 0) {
            this.updateVehicleById(vehicle.copy(id = element.id))
            return ReplaceIfLowerResults.REPLACED
        }
        return ReplaceIfLowerResults.NOT_REPLACED
    }

    override fun getMinById(): Vehicle? {
        if(map.firstEntry() == null) {
            return null
        }
        return map.firstEntry().component2()
    }

    override fun countByType(type: VehicleType?): Int {
        return this.map.filter { it.component2().type == type }.size
    }

    override fun countLessThanEnginePower(enginePower: Double): Int {
        return this.map.filter { it.component2().enginePower < enginePower }.size
    }


    /**
     * Добавляет в коллекцию одну строку csv файла
     */
    private fun loadOneLine(line: String) {
        val regex = Regex("(?<!\\\\),")
        val args = line.trim().split(regex, 0)
        println(args.size)
        println(args.toString())
        if(args.size != 8) {
            throw Exception("Bad format of a file")
        }

        val id = args[0].toInt()
        val creationDate = java.time.LocalDate.parse(args[4])
        val vehicle = VehicleFactory.createVehicleFromString(
            name = args[1].trim(),
            xStr = args[2],
            yStr = args[3],
            vehicleType = args[6],
            fuelType = args[7],
            enginePowerStr = args[5]
        ).copy(id=id, creationDate=creationDate)
        vehicle.validate()
        this.map[id] = vehicle
    }

    override fun clear() {
        this.map.clear()
    }
}