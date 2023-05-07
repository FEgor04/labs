package lab9.backend.adapter.out.persistence.user

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import lab9.backend.adapter.out.persistence.authorities.AuthorityJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.domain.User
import org.hibernate.Hibernate

@Entity
@Table(name = "users")
data class UserJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,
    @Column(unique = true, nullable = false) @NotBlank val username: String,
    @Column(nullable = false) @NotBlank val password: String,
    @OneToMany(mappedBy = "creator")
    val vehicles: Set<VehicleJpaEntity> = emptySet(),

    @OneToMany(mappedBy = "authorizedTo", fetch = FetchType.EAGER)
    val authorizedBy: Set<AuthorityJpaEntity>,
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    val givenAuthorityTo: Set<AuthorityJpaEntity>
) {
    fun toDomain(): User {
        return if (id != null) {
            User.withID(
                User.UserID(id),
                username,
                password,
                this.authorizedBy.filter { it.canEdit }.map { User.UserID(it.owner.id!!) }.toList(),
                this.authorizedBy.filter { it.canDelete }.map { User.UserID(it.owner.id!!) }.toList(),
                this.givenAuthorityTo.filter { it.canEdit }.map { User.UserID(it.authorizedTo.id!!) }.toList(),
                this.givenAuthorityTo.filter { it.canDelete }.map { User.UserID(it.authorizedTo.id!!) }.toList(),
            )
        } else {
            User.withoutID(
                username,
                password,
                this.authorizedBy.filter { it.canEdit }.map { User.UserID(it.owner.id!!) }.toList(),
                this.authorizedBy.filter { it.canDelete }.map { User.UserID(it.owner.id!!) }.toList(),
                this.givenAuthorityTo.filter { it.canEdit }.map { User.UserID(it.authorizedTo.id!!) }.toList(),
                this.givenAuthorityTo.filter { it.canDelete }.map { User.UserID(it.authorizedTo.id!!) }.toList(),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserJpaEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}