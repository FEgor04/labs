package com.efedorov.lab4.backend.application.service.user

import com.efedorov.lab4.backend.application.port.`in`.user.GetUserUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.domain.User
import jakarta.ejb.EJB
import jakarta.ejb.Stateless

@Stateless
class GetUserServiceBean: GetUserUseCase {
    @EJB
    private lateinit var getUserPort: GetUserPort
    override fun getUserByEmail(email: String): User.withID? {
        return getUserPort.getUserByEmail(email)
    }

}