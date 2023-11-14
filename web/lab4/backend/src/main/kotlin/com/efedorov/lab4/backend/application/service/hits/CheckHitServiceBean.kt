package com.efedorov.lab4.backend.application.service.hits

import com.efedorov.lab4.backend.application.port.`in`.hit.CheckHitUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.history.PersistHistoryEntryPort
import com.efedorov.lab4.backend.application.service.predicates.IsHitPredicate
import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.enterprise.inject.Instance
import jakarta.inject.Inject
import java.time.Duration
import java.time.Instant

@Stateless
class CheckHitServiceBean : CheckHitUseCase {
    @EJB
    private lateinit var persistHistoryEntryPort: PersistHistoryEntryPort

    @Inject
    private lateinit var predicates: Instance<IsHitPredicate>

    override fun checkHitForUser(user: User.withID, x: Double, y: Double, r: Double): HistoryEntry {
        val startTime = Instant.now()
        val success = checkHit(x, y, r)
        val serverTime = Instant.now()
        val executionTime = Duration.between(startTime, serverTime)
        return persistHistoryEntryPort.persistHistoryEntry(
            HistoryEntry(
                HistoryEntry.HistoryEntryID(-1),
                user.id,
                x,
                y,
                r,
                success,
                serverTime,
                executionTime
            )
        )
    }

    private fun checkHit(x: Double, y: Double, r: Double): Boolean {
        return this.predicates.any { it.test(Point(x, y, r)) }
    }

    data class Point(val x: Double, val y: Double, val r: Double)
}