package lab6.client

import lab6.client.presentation.cli.CLIHandler
import lab6.client.data.UDPHandlerService
import lab6.shared.entities.user.User
import java.net.InetAddress
import java.util.*


fun main() {
    val user = User(UUID.randomUUID())
    val handler = UDPHandlerService(InetAddress.getLocalHost(), 1234, user)
    val cliHandler = CLIHandler(handler)
    cliHandler.start(System.`in`.bufferedReader(), System.out.bufferedWriter())
}