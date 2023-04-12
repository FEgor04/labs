package lab8.entities.synchronizer

import kotlinx.serialization.Serializable

@Serializable
sealed class SynchronizerResponse(val error: String?) {
    @Serializable
    class Success(): SynchronizerResponse(null)
}