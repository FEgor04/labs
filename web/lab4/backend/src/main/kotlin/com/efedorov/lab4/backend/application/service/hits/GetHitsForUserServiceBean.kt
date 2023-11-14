package com.efedorov.lab4.backend.application.service.hits

import com.efedorov.lab4.backend.application.port.`in`.hit.GetHitsForUserUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.history.GetEntriesByUserPort
import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User
import jakarta.ejb.EJB
import jakarta.ejb.Stateless

@Stateless
class GetHitsForUserServiceBean: GetHitsForUserUseCase {
    @EJB
    private lateinit var getEntriesByUserPort: GetEntriesByUserPort;

    override fun getHitsForUserByUserId(userId: User.UserID): List<HistoryEntry> {
        return getEntriesByUserPort.getEntriesByUserId(userId)
    }

    override fun getHitsForUserByUserName(userName: String): List<HistoryEntry> {
        return getEntriesByUserPort.getEntriesByUserName(userName)
    }
}