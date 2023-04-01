package lab8.client.exceptions

sealed class ClientException(msg: String) : Exception(msg) {
    class ExecuteScriptRecursion(val fileName: String) : ClientException("recursion while executing script $fileName")
    class AuthException(response: String) : ClientException("bad login. server response: $response")
    class CommandException(val commandName: String, val msg: String) : ClientException(
        "could not execute $commandName: $msg"
    )
}
