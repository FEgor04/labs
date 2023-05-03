package lab9.backend.adapter.out.persistence.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.domain.User
import org.hibernate.Hibernate
import org.springframework.validation.annotation.Validated

@Entity
@Validated
data class UserJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,
    @Column(unique = true, nullable = false) @NotBlank val username: String,
    @Column(nullable = false) @NotBlank val password: String,
    @OneToMany
    val vehicles: Set<VehicleJpaEntity>,
) {

    fun toDomain(): User {
        return if(id != null) {
            User.withID(User.UserID(id!!), username, password)
        } else {
            User.withoutID(username, password)
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