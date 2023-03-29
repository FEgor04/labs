package lab7.exceptions

sealed class ServerException(val msg: String) : Exception(msg) {
    class BadOwnerException : ServerException("this vehicle does not belong to you")
    class AcknowledgePseudoException : ServerException("drop it like it's hot")
}
