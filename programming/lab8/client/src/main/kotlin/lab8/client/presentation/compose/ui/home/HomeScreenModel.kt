package lab8.client.presentation.compose.ui.home

import androidx.compose.runtime.*
import androidx.compose.ui.state.ToggleableState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.registry.screenModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import lab8.client.domain.RemoteCommandHandler
import lab8.client.domain.RemoteSynchronizer
import lab8.client.exceptions.ClientException
import lab8.entities.events.CollectionEvent
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.logger.KCoolLogger
import org.koin.java.KoinJavaComponent.inject

class HomeScreenModel(private val coroutineScope: CoroutineScope) : ScreenModel {
    private val logger by KCoolLogger()
    private val service by inject<RemoteCommandHandler>(RemoteCommandHandler::class.java)
    private val synchronizer by inject<RemoteSynchronizer>(RemoteSynchronizer::class.java)
    var error: Exception? by mutableStateOf(null)

    suspend fun logout() {
        service.logout()
        synchronizer.unsubscribe()
    }

    var checkedVehicles by mutableStateOf(HashSet<Int>())
        private set
    private var data: List<Vehicle> by mutableStateOf(listOf<Vehicle>())
    val user: User = service.user
    var allChecked by mutableStateOf(ToggleableState(false))
    var isAddCommandDialogOpen by mutableStateOf(false)
    var isRemoveLowerDialogOpen by mutableStateOf(false)
    var isRemoveGreaterDialogOpen by mutableStateOf(false)
    var isReplaceIfLowerDialogOpen by mutableStateOf(false)
    var editVehicleDialogCurrentId: Int? by mutableStateOf(null)
    var isVisualizerOpen by mutableStateOf(false)

    fun onParentCheck() {
        if (allChecked == ToggleableState.On) {
            checkedVehicles = hashSetOf()
            allChecked = ToggleableState.Off
        } else {
            checkedVehicles = data.filter { it.authorID == user.id }.map { it.id }.toHashSet()
            allChecked = ToggleableState.On
        }
    }


    fun getFilteredData(): List<Vehicle> {
        return data.filter(filterPredicate).sortedByFieldId(sortKey, isAscending).toList()
    }

    fun applySort(newKey: Int) {
        if (newKey == sortKey) {
            sortKey = newKey
            isAscending = !isAscending
        } else {
            sortKey = newKey
            isAscending = true
        }
    }

    fun checkVehicle(id: Int, isChecked: Boolean) {
        val newVehicles = HashSet<Int>()
        newVehicles.addAll(checkedVehicles)
        if (isChecked) {
            newVehicles.add(id)
        } else {
            newVehicles.remove(id)
        }
        checkedVehicles = newVehicles
        updateCheckedVehicles()
    }

    suspend fun replaceIfLower(veh: Vehicle) {
        try {
            service.replaceIfLower(veh.id, veh)
        }
        catch(e: Exception) {
            this.error = e
        }
    }

    private fun updateCheckedVehicles() {
        checkedVehicles = this.checkedVehicles
            .filter { it in data.map { it.id } }
            .toHashSet()
        allChecked = if (checkedVehicles.isEmpty()) {
            ToggleableState.Off
        } else if (checkedVehicles.size == data.size) {
            ToggleableState.On
        } else {
            ToggleableState.Indeterminate
        }
    }

    suspend fun forceUpdate() {
        logger.info("Updating data by force")
        this.data = service.show()
        logger.info("Data update finished")
        logger.info(this.data.map { it.toString() })
        updateCheckedVehicles()
    }

    var filterPredicate: (Vehicle) -> Boolean by mutableStateOf({ true })
    var isLoading by mutableStateOf(false)
    var sortKey by mutableStateOf(0)
    var isAscending by mutableStateOf(true)

    suspend fun removeVehicles() {
        try {
            checkedVehicles.forEach {
                service.remove(it)
            }
        }
        catch (e: Exception) {
            this.error = e
        }
    }

    private suspend fun startUpdater() {
        logger.info("Started updater")
        val channel = synchronizer.subscribe()
        channel.collect {
            logger.info("New event from server: $it")
            if(it is CollectionEvent.ForceUpdate) {
                forceUpdate()
            }
            updateCheckedVehicles()
        }
    }

    fun getVehicleById(id: Int): Vehicle? {
        return data.find { it.id == id }
    }

    suspend fun addVehicle(veh: Vehicle) {
        try {
            service.add(veh)
        }
        catch (e: Exception) {
            this.error = e
        }
    }

    suspend fun removeLower(veh: Vehicle) {
        try {
            service.removeLower(veh)
        }
        catch (e: Exception) {
            this.error = e
        }
    }

    suspend fun removeGreater(veh: Vehicle) {
        try {
            service.removeGreater(veh)
        }
        catch (e: ClientException) {
            this.error = e
        }
    }

    suspend fun updateVehicle(veh: Vehicle) {
        try {
            service.updateVehicleById(veh)
        }
        catch (e: Exception) {
            this.error = e
        }
    }

    init {
        data = runBlocking { service.show() }
        isLoading = false
        coroutineScope.launch {
            startUpdater()
        }
    }
}


fun Iterable<Vehicle>.sortedByFieldId(id: Int, asc: Boolean): Iterable<Vehicle> {
    val ans = when (id) {
        1 -> this.sortedBy { it.name }
        2 -> this.sortedBy { it.coordinates }
        3 -> this.sortedBy { it.creationDate }
        4 -> this.sortedBy { it.enginePower }
        5 -> this.sortedBy { it.type }
        6 -> this.sortedBy { it.fuelType }
        7 -> this.sortedBy { it.authorID }

        else -> this.sortedBy { it.id }
    }
    if (!asc) {
        return ans.reversed()
    } else {
        return ans
    }
}
