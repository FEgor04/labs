package lab9.backend.adapter.out.persistence.authorities

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthoritiesRepository : JpaRepository<AuthorityJpaEntity, AuthorityEntityPK> {
    fun getAllByAuthorizedToIdAndCanDeleteIs(authorizedToId: Int, canDelete: Boolean): List<AuthorityJpaEntity>
    fun getAllByAuthorizedToIdAndCanEditIs(authorizedToId: Int, canEdit: Boolean): List<AuthorityJpaEntity>
    fun findFirstByAuthorizedToIdIsAndOwnerIdIs(authorizedToId: Int, ownerId: Int): AuthorityJpaEntity?
    fun getAllByOwnerIdIsAndCanDeleteIs(ownerId: Int, canDelete: Boolean): List<AuthorityJpaEntity>
    fun getAllByOwnerIdIsAndCanEditIs(ownerId: Int, canEdit: Boolean): List<AuthorityJpaEntity>
}