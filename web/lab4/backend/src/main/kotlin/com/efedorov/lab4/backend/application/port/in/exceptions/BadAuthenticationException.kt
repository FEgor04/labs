package com.efedorov.lab4.backend.application.port.`in`.exceptions

class BadAuthenticationException(username: String) :
    Exception("User with username=${username} does not exists or password is wrong.") {
}