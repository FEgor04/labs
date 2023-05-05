package lab9.backend.adapter.out.persistence.authorities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AuthorityEntityPK(
    @Column(name="owner_id")
    val ownerId: Int,
    @Column(name="authorized_id")
    val authorizedId: Int,
): Serializable