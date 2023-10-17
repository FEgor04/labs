package com.efedorov.lab3.model.index

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Named
import java.time.Instant
import java.time.ZoneId
import java.util.*

@Named
@RequestScoped
class IndexBean {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val timeZone = TimeZone.getTimeZone(zoneId)
    fun getCurrentTime() = Instant.now();
}