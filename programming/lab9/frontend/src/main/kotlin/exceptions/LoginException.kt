package exceptions

sealed class LoginException(val msg: String) : UserServiceException(msg)

class BadUsernameOrPasswordException(): LoginException("Bad username or password")