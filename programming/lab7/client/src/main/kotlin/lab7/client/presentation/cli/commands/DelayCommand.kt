package lab7.client.presentation.cli.commands

import lab7.client.domain.RemoteCommandHandler
import lab7.client.presentation.cli.writeln

/**
 * Класс команды delay.
 * Нужна для теста многопоточности.
 */
class DelayCommand(handler: RemoteCommandHandler) : CommandImpl(
    "delay",
    "обождать 5 секунд",
    "",
    fun(_, writer, _, _, _) {
        writer.writeln("Ждем!")
        writer.flush()
        handler.await()
        writer.writeln("Обождено!")
        writer.flush()
    },
)
