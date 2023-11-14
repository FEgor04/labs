package com.efedorov.lab4.backend.domain

import java.time.Duration
import java.time.Instant

data class HistoryEntry(
    val id: HistoryEntryID,
    val userId: User.UserID,
    val x: Double,
    val y: Double,
    val r: Double,
    val hit: Boolean,
    val serverTime: Instant,
    val executionDuration: Duration
) {
    data class HistoryEntryID(val value: Long)
}