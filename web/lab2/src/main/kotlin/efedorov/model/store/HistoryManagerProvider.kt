package efedorov.model.store

object HistoryManagerProvider {
    private val instance: HistoryManager = SessionHistoryManager()

    fun getInstance(): HistoryManager {
        return instance
    }
}