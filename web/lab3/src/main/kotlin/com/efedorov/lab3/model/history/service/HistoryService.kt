package com.efedorov.lab3.model.history.service

import com.efedorov.lab3.model.HistoryEntry
import jakarta.enterprise.context.SessionScoped

interface HistoryService {
    fun getHistory(): List<HistoryEntry>
    fun add(entry: HistoryEntry)
}