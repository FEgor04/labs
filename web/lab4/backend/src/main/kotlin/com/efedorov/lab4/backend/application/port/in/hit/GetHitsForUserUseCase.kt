package com.efedorov.lab4.backend.application.port.`in`.hit

import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User

interface GetHitsForUserUseCase {
    fun getHitsForUserByUserId(userId: User.UserID): List<HistoryEntry>
    fun getHitsForUserByUserName(userName: String): List<HistoryEntry>
}