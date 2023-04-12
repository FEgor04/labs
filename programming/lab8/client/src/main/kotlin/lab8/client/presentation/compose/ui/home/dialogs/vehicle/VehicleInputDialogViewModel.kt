package lab8.client.presentation.compose.ui.home.dialogs.vehicle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import lab8.entities.vehicle.Coordinates
import lab8.entities.vehicle.FuelType
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import java.time.LocalDate

class VehicleInputDialogViewModel(defaultVehId: Int?, veh: Vehicle?) {
    var vehicleId by mutableStateOf(TextFieldValue(defaultVehId?.toString() ?: ""))
    var vehicleName by mutableStateOf(TextFieldValue(veh?.name ?: ""))
    var vehicleEnginePower by mutableStateOf(TextFieldValue(veh?.enginePower?.toString() ?: ""))
    var vehicleXCoordinate by mutableStateOf(TextFieldValue(veh?.coordinates?.x?.toString() ?: ""))
    var vehicleYCoordinate by mutableStateOf(TextFieldValue(veh?.coordinates?.y?.toString() ?: ""))
    var vehicleType: VehicleType? by mutableStateOf(veh?.type ?: VehicleType.BICYCLE)
    var fuelType by mutableStateOf(veh?.fuelType ?: FuelType.ANTIMATTER)

    fun validate(): Vehicle {
        val veh = Vehicle(
            id = vehicleId.text.toIntOrNull() ?: 1,
            vehicleName.text,
            Coordinates(
                vehicleXCoordinate.text.toInt(),
                vehicleYCoordinate.text.toLong(),
            ),
            LocalDate.now(),
            vehicleEnginePower.text.toDouble(),
            vehicleType,
            fuelType,
            -1,
        )
        veh.validate()
        return veh
    }
}