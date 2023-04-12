package lab8.client.presentation.compose.ui.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lab8.client.presentation.compose.ui.home.dialogs.vehicle.VehicleInputDialog
import lab8.client.presentation.compose.ui.home.table.VehicleTable
import lab8.client.presentation.compose.ui.home.visualization.VisualziationScreen
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider

class HomeScreen(val provider: InternationalizationProvider) : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val localization = provider.chosen
        val coroutineScope = rememberCoroutineScope()
        val screenModel = rememberScreenModel { HomeScreenModel(coroutineScope) }
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                HomeAppBar(
                    currentUser = screenModel.user,
                    localization = localization.homeAppBar,
                    supportedLocales = provider.getSupportedLocales(),
                    isDeleteEnabled = screenModel.checkedVehicles.isNotEmpty(),
                    onDelete = { coroutineScope.launch { screenModel.removeVehicles() } },
                    onRefresh = { coroutineScope.launch { screenModel.forceUpdate() } },
                    onAddButton = { screenModel.isAddCommandDialogOpen = true },
                    onRemoveLowerButton = { screenModel.isRemoveLowerDialogOpen = true },
                    onRemoveGreaterButton = { screenModel.isRemoveGreaterDialogOpen = true },
                    onReplaceIfLowerButton = { screenModel.isReplaceIfLowerDialogOpen = true },
                    onVisualizeButton = { screenModel.isVisualizerOpen = true },
                    changeLocale = { provider.setLocale(it) },
                    onLogout = {
                        coroutineScope.launch {
                            screenModel.logout()
                            navigator.pop()
                        }
                    }
                )
            }
        ) {
            val filteredData = screenModel.getFilteredData()
            if (screenModel.isLoading) {
                CircularProgressIndicator()
            } else {
                if (screenModel.error != null) {
                    Dialog(
                        { screenModel.error = null },
                        title = "ACHTUNG!! ERRORRR!!!",
                    )
                    { Text(provider.chosen.translateException(screenModel.error!!)) }
                }
                val dialogState = DialogState(size = DpSize(800.dp, 800.dp))
                if (screenModel.isAddCommandDialogOpen) {
                    Dialog({ screenModel.isAddCommandDialogOpen = false }, state = dialogState) {
                        VehicleInputDialog(
                            localization.vehicleDialog,
                            { coroutineScope.launch { screenModel.addVehicle(it) } },
                            { screenModel.isAddCommandDialogOpen = false })
                    }
                }
                if (screenModel.isRemoveLowerDialogOpen) {
                    Dialog({ screenModel.isRemoveLowerDialogOpen = false }, state = dialogState) {
                        VehicleInputDialog(
                            localization.vehicleDialog,
                            { coroutineScope.launch { screenModel.removeLower(it) } },
                            { screenModel.isRemoveLowerDialogOpen = false })
                    }
                }
                if (screenModel.isRemoveGreaterDialogOpen) {
                    Dialog({ screenModel.isRemoveGreaterDialogOpen = false }, state = dialogState) {
                        VehicleInputDialog(
                            localization.vehicleDialog,
                            { coroutineScope.launch { screenModel.removeGreater(it) } },
                            { screenModel.isRemoveGreaterDialogOpen = false })
                    }
                }
                if (screenModel.editVehicleDialogCurrentId != null) {
                    Dialog({ screenModel.editVehicleDialogCurrentId = null }, state = dialogState) {
                        VehicleInputDialog(
                            localization.vehicleDialog,
                            { coroutineScope.launch { screenModel.updateVehicle(it) } },
                            { screenModel.isRemoveGreaterDialogOpen = false },
                            vehicleId = screenModel.editVehicleDialogCurrentId,
                            defaultValues = screenModel.getVehicleById(screenModel.editVehicleDialogCurrentId!!),
                            vehicleIdEnabled = false
                        )
                    }
                }
                if (screenModel.isVisualizerOpen) {
                    Dialog(
                        { screenModel.isVisualizerOpen = false },
                        state = DialogState(size = DpSize(1000.dp, 1000.dp))
                    ) {
                        VisualziationScreen(screenModel.getFilteredData(), {
                            println("Handling car click. Showing dialog.")
                            screenModel.editVehicleDialogCurrentId = it
                        })
                    }
                }
                if (screenModel.isReplaceIfLowerDialogOpen) {
                    Dialog({ screenModel.isReplaceIfLowerDialogOpen = false }, state = dialogState) {
                        VehicleInputDialog(
                            localization.vehicleDialog,
                            { coroutineScope.launch { screenModel.replaceIfLower(it) } },
                            onCancel = { screenModel.isReplaceIfLowerDialogOpen = false },
                            vehicleId = 0,
                            vehicleIdEnabled = true,
                        )
                    }
                }

                VehicleTable(
                    localization,
                    filteredData,
                    screenModel.user,
                    screenModel.sortKey,
                    screenModel.isAscending,
                    checkedVehicles = screenModel.checkedVehicles,
                    allCheckedEnabled = filteredData.filter { it.authorID == screenModel.user.id }.isNotEmpty(),
                    areAllChecked = screenModel.allChecked,
                    onCheckAll = { screenModel.onParentCheck() },
                    onCheckVehicle = { id, isChecked ->
                        screenModel.checkVehicle(id, isChecked)
                    },
                    onApplyFilter = {
                        screenModel.filterPredicate = it
                    },
                    onApplySort = {
                        screenModel.applySort(it)
                    },
                    onEditVehicle = {
                        screenModel.editVehicleDialogCurrentId = it
                    }
                )
            }
        }
    }
}
