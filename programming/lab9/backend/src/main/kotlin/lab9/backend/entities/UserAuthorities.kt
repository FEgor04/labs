package lab9.backend.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import java.io.Serial
import java.io.Serializable

@Entity
@kotlinx.serialization.Serializable
data class UserAuthorities(
    @EmbeddedId
    val id: UserAuthoritiesPK,
    @ManyToOne
    @MapsId("ownerId")
    @JoinColumn(name = "OWNER_ID")
    val owner: User,
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    val user: User,

    val canEdit: Boolean,
    val canDelete: Boolean
)

@Embeddable
@kotlinx.serialization.Serializable
data class UserAuthoritiesPK (
    @Column
    val ownerId: Int,
    @Column
    val userId: Int
): Serializable

