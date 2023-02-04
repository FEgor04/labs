package lab5.cli.commands

import io.mockk.*
import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.FuelType
import lab5.entities.vehicle.VehicleFactory
import lab5.entities.vehicle.VehicleType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateCommandTest : CommandTest() {
    @Test
    fun ok() {
        mockkObject(ReaderUtils)

        val oldVehicle = VehicleFactory.generateRandomVehicle()
        val newVehicle =
            VehicleFactory.generateRandomVehicle().copy(id = oldVehicle.id, creationDate = oldVehicle.creationDate)
        every { repository.getVehicleById(oldVehicle.id) } returns oldVehicle
        every {
            (ReaderUtils.readType<String?>(
                reader, writer,
                hint = "Введите новое имя или оставьте пустым чтобы оставить старое значение = ${oldVehicle.name}.\n",
                caster = any(),
                validator = any()
            ))
        } returns newVehicle.name
        every {
            (ReaderUtils.readType<Int?>(
                reader, writer,
                hint = "Введите новое значение координаты X или оставьте пустым чтобы оставить старое значение = ${oldVehicle.coordinates.x}\n",
                caster = ReaderUtils.toIntOrNullCaster,
                validator = any()
            ))
        } returns newVehicle.coordinates.x
        every {
            ReaderUtils.readType(
                reader, writer,
                hint = "Введите новое значение координаты Y или оставьте пустым чтобы ввести null\n",
                caster = ReaderUtils.toLongOrNullCaster,
                validator = any()
            )
        } returns newVehicle.coordinates.y
        every {
            ReaderUtils.readType(
                reader, writer,
                hint = "Введите новое значение мощности двигателя или оставьте пустым чтобы оставить старое значение = ${oldVehicle.enginePower}\n",
                caster = ReaderUtils.toDoubleOrNullCaster,
                validator = any()
            )
        } returns newVehicle.enginePower
        every {
            ReaderUtils.readType<VehicleType?>(
                reader, writer,
                hint = "Введите новое значение типа транспорта (${
                    VehicleType.values().map { it.toString() }
                }) или оставьте пустым для null.\n",
                caster = any(),
                validator = any(),
            )
        } returns newVehicle.type
        every {
            ReaderUtils.readType<FuelType?>(reader, writer,
                hint = "Введите новое значение типа топлива (${
                    FuelType.values().map { it.toString() }
                }) или оставьте пустым для старого значения = ${oldVehicle.fuelType}\n",
                caster = any(),
                validator = any()
            )
        } returns newVehicle.fuelType
        every {
            repository.updateVehicleById(newVehicle)
        } returns Unit

        val cmd = UpdateCommand(repository)
        cmd.handle("update ${oldVehicle.id}", writer, reader)

        verify {
            writer.write("Успех!\n")
            writer.flush()
        }
        confirmVerified(writer)

        verify {
            repository.getVehicleById(oldVehicle.id)
            repository.updateVehicleById(newVehicle)
        }
        confirmVerified(repository)
    }

    @Test fun `no vehicle`() {
        every { repository.getVehicleById(123) } returns null
        val cmd = UpdateCommand(repository)
        cmd.handle("update 123", writer, reader)
        verify {
            writer.write("Нет такого автомобиля. Сначала добавь\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `id is not an int`() {
        val cmd = UpdateCommand(repository)
        cmd.handle("update notAnInteger", writer, reader)
        verify {
            writer.write("Неправильный ввод. Попробуйте еще.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `empty input`() {
        val cmd = UpdateCommand(repository)
        cmd.handle("update", writer, reader)
        verify {
            writer.write("Неправильный ввод. Попробуйте еще.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `everything is null`() {
        mockkObject(ReaderUtils)

        val oldVehicle = VehicleFactory.generateRandomVehicle()
        val newVehicle =
            VehicleFactory.generateRandomVehicle().copy(id = oldVehicle.id,
                creationDate = oldVehicle.creationDate,
                name = oldVehicle.name,
                coordinates = oldVehicle.coordinates.copy(y=null),
                fuelType = oldVehicle.fuelType,
                type=null,
                enginePower = oldVehicle.enginePower
            )
        every { repository.getVehicleById(oldVehicle.id) } returns oldVehicle
        every {
            (ReaderUtils.readType<String?>(
                reader, writer,
                hint = "Введите новое имя или оставьте пустым чтобы оставить старое значение = ${oldVehicle.name}.\n",
                caster = any(),
                validator = any()
            ))
        } returns null
        every {
            (ReaderUtils.readType<Int?>(
                reader, writer,
                hint = "Введите новое значение координаты X или оставьте пустым чтобы оставить старое значение = ${oldVehicle.coordinates.x}\n",
                caster = ReaderUtils.toIntOrNullCaster,
                validator = any()
            ))
        } returns null
        every {
            ReaderUtils.readType(
                reader, writer,
                hint = "Введите новое значение координаты Y или оставьте пустым чтобы ввести null\n",
                caster = ReaderUtils.toLongOrNullCaster,
                validator = any()
            )
        } returns null
        every {
            ReaderUtils.readType(
                reader, writer,
                hint = "Введите новое значение мощности двигателя или оставьте пустым чтобы оставить старое значение = ${oldVehicle.enginePower}\n",
                caster = ReaderUtils.toDoubleOrNullCaster,
                validator = any()
            )
        } returns null
        every {
            ReaderUtils.readType<VehicleType?>(
                reader, writer,
                hint = "Введите новое значение типа транспорта (${
                    VehicleType.values().map { it.toString() }
                }) или оставьте пустым для null.\n",
                caster = any(),
                validator = any(),
            )
        } returns null
        every {
            ReaderUtils.readType<FuelType?>(reader, writer,
                hint = "Введите новое значение типа топлива (${
                    FuelType.values().map { it.toString() }
                }) или оставьте пустым для старого значения = ${oldVehicle.fuelType}\n",
                caster = any(),
                validator = any()
            )
        } returns null
        every {
            repository.updateVehicleById(newVehicle)
        } returns Unit

        val cmd = UpdateCommand(repository)
        cmd.handle("update ${oldVehicle.id}", writer, reader)

        verify {
            writer.write("Успех!\n")
            writer.flush()
        }
        confirmVerified(writer)

        verify {
            repository.getVehicleById(oldVehicle.id)
            repository.updateVehicleById(newVehicle)
        }
        confirmVerified(repository)
    }

    @AfterAll
    fun afterTests() {
        unmockkAll()
    }
}