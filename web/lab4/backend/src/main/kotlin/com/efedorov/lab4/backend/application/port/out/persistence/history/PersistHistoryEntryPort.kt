package com.efedorov.lab4.backend.application.port.out.persistence.history

import com.efedorov.lab4.backend.domain.HistoryEntry

interface PersistHistoryEntryPort {
    fun persistHistoryEntry(historyEntry: HistoryEntry): HistoryEntry
}