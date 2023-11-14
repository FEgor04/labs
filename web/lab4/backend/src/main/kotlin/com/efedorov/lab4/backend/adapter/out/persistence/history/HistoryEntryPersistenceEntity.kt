package com.efedorov.lab4.backend.adapter.out.persistence.history

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Duration
import java.time.Instant

@Entity
@Table(name = "history_entry")
data class HistoryEntryPersistenceEntity(
    @Id
    @GeneratedValue
    val id: Long?,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserPersistenceEntity,
    val x: Double,
    val y: Double,
    val r: Double,
    val hit: Boolean,
    val serverTime: Instant,
    val executionTime: Duration
) {
    fun toEntity(): HistoryEntry =
        HistoryEntry(
            HistoryEntry.HistoryEntryID(this.id!!),
            User.UserID(this.user.id!!),
            this.x,
            this.y,
            this.r,
            this.hit,
            this.serverTime,
            this.executionTime
        )
}