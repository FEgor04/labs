package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.entities.FactoryException
import lab5.entities.ValidationException
import lab5.entities.vehicle.*
import lab5.repositories.VehicleRepository
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Класс команды insert
 */
class InsertCommand(repository: VehicleRepository): CommandImpl(
    "insert",
    "добавить новый элемент с заданным ключом",
    "",
    fun (_, writer, reader, _) {
        val vehicle = ReaderUtils.readVehicle(writer, reader)
        repository.insertVehicle(vehicle)
        writer.write("Успех!\n")
        writer.flush()
    },
)
