package com.efedorov.lab3.model

import com.efedorov.lab3.lib.InstantSerializer
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.io.Serializable
import java.time.Instant

@Entity
@kotlinx.serialization.Serializable
data class HistoryEntry(
    @Id
    @GeneratedValue
    val id: Long?,
    val x: Double,
    val y: Double,
    val r: Double,
    val hit: Boolean,
    @kotlinx.serialization.Serializable(with = InstantSerializer::class)
    val time: Instant,
): Serializable