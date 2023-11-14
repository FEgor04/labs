package com.efedorov.lab4.backend.application.port.`in`.exceptions

class BadRefreshTokenException: Exception("Refresh token is already used or not valid.")