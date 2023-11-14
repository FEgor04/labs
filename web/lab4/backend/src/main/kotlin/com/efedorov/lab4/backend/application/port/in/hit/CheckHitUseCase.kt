package com.efedorov.lab4.backend.application.port.`in`.hit

import com.efedorov.lab4.backend.domain.HistoryEntry
import com.efedorov.lab4.backend.domain.User

interface CheckHitUseCase {
    fun checkHitForUser(user: User.withID, x: Double, y: Double, r: Double): HistoryEntry
}