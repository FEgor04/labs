package lab8.client.presentation.compose.ui.home.filters.enum

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import lab8.client.presentation.compose.ui.fields.EnumCheckboxField
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider
import lab8.entities.vehicle.VehicleType

@Composable
inline fun <reified E : Enum<E>> EnumFilterDialogContent(
    i18n: Internationalization.EnumFilterDialog,
    isNullable: Boolean,
    crossinline setFilterPredicate: (values: Set<E?>) -> Unit,
    crossinline clearPredicate: () -> Unit,
) {
    val viewModel by remember { mutableStateOf(EnumFilterDialogViewModel<E>()) }
    Column(
        modifier = Modifier.fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EnumCheckboxField(
            isNullable,
            viewModel.selectedValues,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) { value, isChecked ->
            viewModel.checkValue(value, isChecked)
        }
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                setFilterPredicate(viewModel.selectedValues)
            }) {
                Text(i18n.confirm)
            }
            TextButton({
                clearPredicate()
            }) {
                Text(i18n.cancel)
            }
        }
    }
}

@Composable
@Preview
fun EnumFilterDialogContentPreview() {
    EnumFilterDialogContent<VehicleType>(PropertiesInternationalizationProvider().chosen.enumFilterDialog, false, {}) {}
}
