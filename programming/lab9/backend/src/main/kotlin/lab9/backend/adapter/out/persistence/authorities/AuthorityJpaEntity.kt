package lab9.backend.adapter.out.persistence.authorities

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import lab9.backend.adapter.out.persistence.user.UserJpaEntity

@Entity
@Table(name = "authorizations")
data class AuthorityJpaEntity(
    @EmbeddedId
    val id: AuthorityEntityPK?,
    @ManyToOne
    @MapsId("ownerId")
    @JoinColumn(name = "owner_id")
    val owner: UserJpaEntity,
    @ManyToOne
    @MapsId("authorizedId")
    @JoinColumn(name = "authorized_id")
    val authorizedTo: UserJpaEntity,
    val canEdit: Boolean,
    val canDelete: Boolean
)