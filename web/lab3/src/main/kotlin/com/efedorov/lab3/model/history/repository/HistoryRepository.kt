package com.efedorov.lab3.model.history.repository

import com.efedorov.lab3.model.HistoryEntry
import java.io.Serializable

interface HistoryRepository: Serializable {

     fun create(entry: HistoryEntry): HistoryEntry

    fun list(): List<HistoryEntry>
}