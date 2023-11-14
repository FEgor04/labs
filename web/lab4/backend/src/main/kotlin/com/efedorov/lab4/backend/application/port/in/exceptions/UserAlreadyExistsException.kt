package com.efedorov.lab4.backend.application.port.`in`.exceptions

class UserAlreadyExistsException(userName: String): Exception("User with username = $userName already exists.") {
}