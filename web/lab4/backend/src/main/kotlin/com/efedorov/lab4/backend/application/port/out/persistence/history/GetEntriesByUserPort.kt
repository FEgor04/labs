package com.efedorov.lab4.backend.application.port.out.persistence.history

import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User

interface GetEntriesByUserPort {
    fun getEntriesByUserId(userId: User.UserID): List<HistoryEntry>
    fun getEntriesByUserName(userName: String): List<HistoryEntry>
}