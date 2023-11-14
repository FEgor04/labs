package com.efedorov.lab4.backend.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/api")
class Application: Application() {
}