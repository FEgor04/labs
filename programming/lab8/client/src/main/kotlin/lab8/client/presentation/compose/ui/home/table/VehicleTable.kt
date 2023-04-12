package lab8.client.presentation.compose.ui.home.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Divider
import androidx.compose.material.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle

@Preview
@Composable
fun VehicleTable(
    internationalization: Internationalization,
    data: List<Vehicle> = (1..10).map { Vehicle.generateRandomVehicle() },
    currentUser: User,
    sortKey: Int,
    isAsc: Boolean,
    checkedVehicles: Set<Int>,
    allCheckedEnabled: Boolean,
    areAllChecked: ToggleableState,
    onCheckAll: () -> Unit,
    onCheckVehicle: (Int, Boolean) -> Unit,
    onEditVehicle: (Int) -> Unit,
    onApplyFilter: ((Vehicle) -> Boolean) -> Unit,
    onApplySort: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val headers = internationalization.dataTable.tableHeaders
    val isNumeric: (Int) -> Boolean = {
        it in listOf(0, 4, 7)
    }

    val state = rememberLazyListState()
    val columnsNumber = headers.size
    Box(modifier) {
        LazyColumn(Modifier.fillMaxSize(), state) {
            item {
                Divider()
                TableRow(
                    modifier = Modifier.requiredHeight(56.dp)
                ) {
                    TriStateCheckbox(
                        areAllChecked,
                        { onCheckAll() },
                        enabled = allCheckedEnabled,
                    )
                    Spacer(Modifier.width(48.dp))
                    headers.mapIndexed { colId, it ->
                        TableHeaderCell(internationalization,
                            it,
                            1f / columnsNumber,
                            getHeaderSortStatus(colId, sortKey, isAsc),
                            isNumeric(colId),
                            colId,
                            {
                                onApplyFilter(it)
                            }) { onApplySort(colId) }
                    }
                }
                Divider()
            }

            items(data.size, key = { data[it].id }) {
                TableDataRow(
                    i18n = internationalization,
                    vehicle = data[it],
                    isDeletable = data[it].authorID == currentUser.id,
                    isCheckedToDelete = data[it].id in checkedVehicles,
                    onChecked = { isChecked -> onCheckVehicle(data[it].id, isChecked) },
                    onEdit = if (data[it].authorID == currentUser.id) {
                        { onEditVehicle(data[it].id) }
                    } else {
                        null
                    },
                    isNumeric = isNumeric,
                )
                Divider()
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(), adapter = rememberScrollbarAdapter(state)
        )
    }
}