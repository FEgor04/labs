package lab8.entities.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class RegisterServerResponse(val message: String, val number: Int) : ResponseDTO() {
    override val name: String
        get() = "here you gou"
    override val error: String?
        get() = null
}
