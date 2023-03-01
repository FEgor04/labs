package lab6.server.data.handlers.commands

import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.ResponseDTO
import java.util.*

interface CommandHandler {
    fun check(requestDTO: RequestDTO): Boolean
    fun handle(requestDTO: RequestDTO): ResponseDTO
}

abstract class DefaultCommandHandler(val name: String): CommandHandler {
    override fun check(requestDTO: RequestDTO): Boolean {
        val answer = requestDTO.name.trim().lowercase() == this.name.lowercase()
        return answer
    }

    override fun toString(): String {
        return "$name command"
    }
}