package com.efedorov.lab4.backend.application.port.out.web.oauth

class BadAccessTokenException: Exception("Provided access token is not valid. Try to authenticate again.")