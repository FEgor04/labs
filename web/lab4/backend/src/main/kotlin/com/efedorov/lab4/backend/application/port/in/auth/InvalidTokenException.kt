package com.efedorov.lab4.backend.application.port.`in`.auth

import java.lang.Exception

class InvalidTokenException: Exception("Provided token is not valid."){
}