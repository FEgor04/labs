package lab8.client.presentation.compose.ui.home.filters.number

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.fields.NumberTextField
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider

@Composable
fun NumberFilterDialogContent(
    i18n: Internationalization.NumberFilterDialog,
    setFilterPredicate: (leftLimit: Float, rightLimit: Float) -> Unit,
    clearPredicate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel by remember { mutableStateOf(NumberFilterDialogViewModel()) }
    Column(
        modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(i18n.valueShouldBeGreaterThan)
            NumberTextField(
                viewModel.lowerBorder,
                Float.MIN_VALUE,
                Float.MAX_VALUE,
                { it.toFloat() }) { viewModel.lowerBorder = it }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(i18n.valueShouldBeLessThan)
            NumberTextField(
                viewModel.upperBorder,
                Float.MIN_VALUE,
                Float.MAX_VALUE,
                { it.toFloat() }) { viewModel.upperBorder = it }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button({ setFilterPredicate(viewModel.lowerBorderAsFloat(), viewModel.upperBorderAsFloat()) }) {
                Text(i18n.confirm)
            }
            TextButton({ clearPredicate() }) {
                Text(i18n.cancel)
            }
        }
    }

}

@Composable
@Preview
fun FilterNumericDialogContentPreview() {
    NumberFilterDialogContent(PropertiesInternationalizationProvider().chosen.numberFilterDialog, { _, _ -> }, {})
}
