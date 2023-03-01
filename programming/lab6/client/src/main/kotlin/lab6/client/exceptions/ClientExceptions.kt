package lab6.client.exceptions

sealed class ClientException(val msg: String): Exception(msg) {
    class ExecuteScriptRecursion(val fileName: String): ClientException("recursion while executing script $fileName")
}
