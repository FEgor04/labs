package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.ResponseDTO
import kotlin.reflect.KClass

interface Command {
    fun check(requestDTO: RequestDTO): Boolean
    suspend fun execute(requestDTO: RequestDTO): ResponseDTO
}

sealed class DefaultCommand(private val expectedClass: KClass<out RequestDTO>) : Command {
    override fun check(requestDTO: RequestDTO): Boolean {
        return expectedClass == requestDTO::class
    }

    override fun toString(): String {
        return "${expectedClass.simpleName} handler"
    }
}
