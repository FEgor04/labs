package com.efedorov.lab4.backend.adapter.out.persistence.history

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.application.port.out.persistence.history.GetEntriesByUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.history.PersistHistoryEntryPort
import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Stateless
class HistoryEntryPersistenceAdapter : PersistHistoryEntryPort, GetEntriesByUserPort {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @EJB
    private lateinit var getUserPort: GetUserPort

    override fun persistHistoryEntry(historyEntry: HistoryEntry): HistoryEntry {
        val persistenceEntity = HistoryEntryPersistenceEntity(
            null,
            UserPersistenceEntity(historyEntry.userId.value, "", emptySet()),
            historyEntry.x,
            historyEntry.y,
            historyEntry.r,
            historyEntry.hit,
            historyEntry.serverTime,
            historyEntry.executionDuration
        )
        entityManager.persist(persistenceEntity)
        entityManager.flush()
        return persistenceEntity.toEntity()
    }

    override fun getEntriesByUserId(userId: User.UserID): List<HistoryEntry> {
        return (entityManager.createQuery("SELECT h FROM HistoryEntryPersistenceEntity h where h.user.id = :userId")
            .setParameter("userId", userId)
            .resultList.toList() as List<HistoryEntryPersistenceEntity>).map {
            it.toEntity()
        }
    }

    override fun getEntriesByUserName(userName: String): List<HistoryEntry> {
        return (entityManager.createQuery("SELECT h FROM HistoryEntryPersistenceEntity h where h.user.email = :userName")
            .setParameter("userName", userName)
            .resultList.toList() as List<HistoryEntryPersistenceEntity>).map {
            it.toEntity()
        }
    }
}