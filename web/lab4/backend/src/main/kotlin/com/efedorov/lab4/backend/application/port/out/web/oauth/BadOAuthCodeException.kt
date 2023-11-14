package com.efedorov.lab4.backend.application.port.out.web.oauth

class BadOAuthCodeException: Exception("Provided code is not valid. Try to authenticate again.")