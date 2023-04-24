package lab9.backend.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotBlank
import lab9.common.dto.UserDTO
import lab9.common.dto.VehicleDTO
import lab9.common.responses.ShowUserResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated

@Entity
@Table(name = "users")
@Validated
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,
    @Column(unique = true, nullable = false)
    @NotBlank
    private val username: String,
    @Column(nullable = false)
    @NotBlank
    private val password: String,
    @OneToMany(fetch = FetchType.EAGER)
    val vehicles: Set<Vehicle>,
) : UserDetails {
    override fun getAuthorities(): Collection<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun toShowUserResponse(): ShowUserResponse {
        return ShowUserResponse(id, username, vehicles.map { it.toDTO() }.toTypedArray())
    }

    fun toDTO(): UserDTO {
        return UserDTO(
            id = this.id,
            username = this.username,
        )
    }

    companion object {
        val empty = User(
            id = -1,
            username = "",
            password = "",
            emptySet()
        )
    }
}