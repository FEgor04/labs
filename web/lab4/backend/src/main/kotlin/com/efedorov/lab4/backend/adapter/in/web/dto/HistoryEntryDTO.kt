package com.efedorov.lab4.backend.adapter.`in`.web.dto

import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.domain.HistoryEntry

@DTO
data class HistoryEntryDTO(
    val id: Long,
    val x: Double,
    val y: Double,
    val r: Double,
    val hit: Boolean,
    val serverTime: Long, // UNIX timestamp milli
    val executionDuration: Long, // nanoseconds
) {
    companion object {
        fun fromEntity(entity: HistoryEntry) = HistoryEntryDTO(
            entity.id.value,
            entity.x,
            entity.y,
            entity.r,
            entity.hit,
            entity.serverTime.toEpochMilli(),
            entity.executionDuration.toNanos(),
        )
    }
}