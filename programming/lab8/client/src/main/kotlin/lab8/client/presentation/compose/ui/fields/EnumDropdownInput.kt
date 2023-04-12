package lab8.client.presentation.compose.ui.fields

import ExposedDropdownMenu
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lab8.entities.enumValueOfOrNull
import lab8.entities.vehicle.VehicleType

@Composable
inline fun <reified E : Enum<E>> EnumDropdownInput(
    selected: E?,
    crossinline onSelected: (E?) -> Unit,
    isNullable: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val values = enumValues<E>().map { it.toString() }.toMutableList()
    if (isNullable) {
        values += "null"
    }
    ExposedDropdownMenu(
        values,
        selected?.ordinal ?: (values.size - 1),
        modifier = modifier
    ) { onSelected(enumValueOfOrNull<E>(values[it])) }
}

@Composable
@Preview
fun EnumDropdownInputPreview() {
    EnumDropdownInput<VehicleType>(VehicleType.BICYCLE, {})
}
