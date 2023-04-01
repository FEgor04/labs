package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler
import lab8.client.presentation.cli.writeln

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
