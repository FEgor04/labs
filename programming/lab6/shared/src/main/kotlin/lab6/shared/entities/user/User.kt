package lab6.shared.entities.user

import kotlinx.serialization.Serializable
import lab6.shared.entities.dtos.commands.UUIDSerializer
import java.util.UUID

@Serializable data class User(@Serializable(with = UUIDSerializer::class) val id: UUID)