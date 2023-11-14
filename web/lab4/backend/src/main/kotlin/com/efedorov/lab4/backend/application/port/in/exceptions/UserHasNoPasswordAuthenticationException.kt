package com.efedorov.lab4.backend.application.port.`in`.exceptions

class UserHasNoPasswordAuthenticationException: Exception("User has no password. Try to authenticate using Google / VK")