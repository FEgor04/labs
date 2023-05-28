package lab9.backend.application.port.`in`.signup

data class SignUpCommand(
    val username: String,
    val password: String
) {
    override fun toString(): String {
        return "SignInCommand(username=${username}, password=HIDDEN}"
    }
}
