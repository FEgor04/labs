package lab8.client.presentation.compose.ui.home.dialogs.vehicle

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.fields.EnumDropdownInput
import lab8.client.presentation.compose.ui.fields.NumberTextField
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider
import lab8.entities.vehicle.Coordinates
import lab8.entities.vehicle.FuelType
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VehicleInputDialog(
    i18n: Internationalization.VehicleDialog,
    onConfirm: (Vehicle) -> Unit,
    onCancel: () -> Unit,
    defaultValues: Vehicle? = null,
    vehicleId: Int? = null,
    vehicleIdEnabled: Boolean = false,
) {
    val vm by remember { mutableStateOf(VehicleInputDialogViewModel(vehicleId, defaultValues)) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var validationError by remember { mutableStateOf("") }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            buttons = {},
            text = { Text(validationError) }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(1f).padding(16.dp),
    ) {
        if (vehicleId != null) {
            NumberTextField<Int>(
                value = vm.vehicleId,
                minValue = 0,
                maxValue = Int.MAX_VALUE,
                caster = { it.toInt() },
                label = { Text("ID") },
                isIntegerOnly = true,
                isPositiveOnly = true,
                enabled = vehicleIdEnabled,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                vm.vehicleId = it
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = vm.vehicleName,
            onValueChange = { vm.vehicleName = it },
            label = { Text(i18n.vehicleNameField) },
            modifier = Modifier.fillMaxWidth(0.8f),
        )

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NumberTextField<Int>(
                value = vm.vehicleXCoordinate,
                minValue = Coordinates.xLowerBound,
                maxValue = Int.MAX_VALUE,
                caster = { it.toInt() },
                onChange = {
                    vm.vehicleXCoordinate = it
                },
                isPositiveOnly = true,
                isIntegerOnly = true,
                label = { Text(i18n.vehicleXCoordinateField) },
                modifier = Modifier.weight(0.4f),
            )
            Spacer(Modifier.weight(0.2f))
            NumberTextField<Long>(
                value = vm.vehicleYCoordinate,
                minValue = Long.MIN_VALUE,
                maxValue = Long.MAX_VALUE,
                caster = { it.toLong() },
                onChange = {
                    vm.vehicleYCoordinate = it
                },
                isPositiveOnly = false,
                isIntegerOnly = true,
                label = { Text(i18n.vehicleYCoordinateField) },
                modifier = Modifier.weight(0.4f),
            )
        }

        Spacer(Modifier.height(16.dp))
        NumberTextField(
            value = vm.vehicleEnginePower,
            minValue = Vehicle.enginePowerLowerBound.toFloat(),
            maxValue = Float.MAX_VALUE,
            caster = { it.toFloat() },
            onChange = {
                vm.vehicleEnginePower = it
            },
            label = { Text(i18n.vehicleEnginePowerField) },
            modifier = Modifier.fillMaxWidth(0.8f),
        )

        Spacer(Modifier.height(16.dp))
        EnumDropdownInput<VehicleType>(
            vm.vehicleType,
            { vm.vehicleType = it },
            isNullable = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        Spacer(Modifier.height(16.dp))
        EnumDropdownInput<FuelType>(
            vm.fuelType,
            { vm.fuelType = it!! },
            isNullable = false,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button({
                try {
                    onConfirm(vm.validate())
                } catch (e: Exception) {
                    validationError = e.toString()
                    showAlertDialog = true
                }
            }) {
                Text(i18n.confirm)
            }

            TextButton({ onCancel() }) {
                Text(i18n.cancel)
            }
        }
    }
}

@Composable
@Preview
fun VehicleDialogContentPreview() {
    VehicleInputDialog(PropertiesInternationalizationProvider().chosen.vehicleDialog, {}, {})
}

@Composable
@Preview
fun VehicleDialogContentIdDisabledPreview() {
    VehicleInputDialog(
        PropertiesInternationalizationProvider().chosen.vehicleDialog,
        {},
        {},
        vehicleId = 5,
        vehicleIdEnabled = false
    )
}

@Composable
@Preview
fun VehicleDialogContentIdEnabledPreview() {
     VehicleInputDialog(
        PropertiesInternationalizationProvider().chosen.vehicleDialog,
        {},
        {},
        vehicleId = 0,
        vehicleIdEnabled = true,
    )
}