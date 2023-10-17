package com.efedorov.lab3.model

import com.efedorov.lab3.checkers.*
import com.efedorov.lab3.model.history.service.HistoryService
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.inject.Named
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.Serializable
import java.time.Instant
import java.time.ZoneId
import java.util.TimeZone

@Named
@RequestScoped
class HitCheckerForm : Serializable {
    @Inject
    @Named("combinedHitChecker")
    private lateinit var checker: HitChecker;
    @Inject
    private lateinit var historyService: HistoryService;

    val zoneId: ZoneId = ZoneId.systemDefault()
    val timeZone = TimeZone.getTimeZone(zoneId)

    var x: Double = 0.0
        set(value) {
            field = Math.round(value * 100.0) / 100.0
        }
    var y: Double = 0.0
        set(value) {
            field = Math.round(value * 100.0) / 100.0
        }
    var r: Double = 1.5
        set(value) {
            field = Math.round(value * 100.0) / 100.0
        }


    @PostConstruct
    fun init() {
    }

    fun submit() {
        val point = Point(x, y, r);
        val hit = checker.check(point)
        historyService.add(HistoryEntry(null, x, y, r, hit, Instant.now()))
    }


    fun getEntries(): List<HistoryEntry> = historyService.getHistory()

    fun getEntriesJson(): String = Json.encodeToString(this.getEntries())
}
