package com.efedorov.lab4.backend.application.port.out.persistence.user.token

interface IssueRefreshTokenIdPort {
    /**
     * Создает новый ID для refresh токена и сохраняет его
     */
    fun issueRefreshTokenId(): String
}