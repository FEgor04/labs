package com.efedorov.lab3.model.history.service

import com.efedorov.lab3.model.HistoryEntry
import com.efedorov.lab3.model.history.repository.HistoryRepository
import jakarta.annotation.ManagedBean
import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Inject
import jakarta.inject.Named
import java.io.Serializable

@SessionScoped
@ManagedBean
@Named("sessionScopedHistoryService")
class SessionScopedHistoryService : HistoryService, Serializable {
    @Inject
    private lateinit var historyRepository: HistoryRepository;

    private val associatedIds: MutableList<Long> = mutableListOf();

    override fun getHistory(): List<HistoryEntry> {
        return historyRepository.list()
            .filter { it.id in associatedIds }
    }

    override fun add(entry: HistoryEntry) {
        val entryWithId = historyRepository.create(entry)
        associatedIds.add(entryWithId.id!!)
    }
}