package com.efedorov.lab3.model.history.repository

import com.efedorov.lab3.model.HistoryEntry
import jakarta.annotation.ManagedBean
import jakarta.annotation.Resource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.UserTransaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable

@ManagedBean
@ApplicationScoped
@Named
class HibernateHistoryRepository : HistoryRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Resource
    private lateinit var userTransaction: UserTransaction;

    private val logger: Logger = LoggerFactory.getLogger(HibernateHistoryRepository::class.java)


    override fun create(entry: HistoryEntry): HistoryEntry {
        userTransaction.begin()
        entityManager.persist(entry)
        userTransaction.commit()
        logger.info("Saved new history entry. ID = ${entry.id}")
        return entry
    }

    override fun list() = entityManager
        .createQuery("SELECT h FROM HistoryEntry h", HistoryEntry::class.java)
        .resultList
}