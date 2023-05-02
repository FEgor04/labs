package lab9.backend.domain

class BadAuthenticationException: Exception("Bad password or user does not exists")