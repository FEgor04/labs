package lab6.server

import lab6.server.data.handlers.UDPDatagramHandler
import lab6.server.data.repositories.VehicleCsvRepository
import lab6.server.domain.repositories.VehicleRepository
import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.server.presentation.cli.CLIHandler
import java.io.File
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    val filename = System.getenv("FILENAME")
    val file: File? = if(filename == null || filename.isEmpty()) {
        null
    } else {
        println(filename)
        File(filename)
    }
    val repository: VehicleRepository = VehicleCsvRepository(file)
    repository.load()
    val useCase = CommandsHandlerUseCase(repository)

    Runtime.getRuntime().addShutdownHook(object: Thread() {
        override fun run() {
            useCase.save()
        }
    })

    val udpHandler = UDPDatagramHandler(1234, useCase)
    val th = thread {
        udpHandler.run()
    }
    // TODO: переписать на нормальный выход
//    val cliHandler = CLIHandler(useCase) { exitProcess(0) }
//    cliHandler.start(System.`in`.bufferedReader(), System.out.bufferedWriter())
}