package lab8.client.presentation.compose.ui.home.filters.date

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.fields.DateTextInputField
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider
import java.time.LocalDate

@Composable
fun DateFilterDialogContent(
    internationalization: Internationalization.DateFilterDialog,
    setFilterPredicate: (leftLimit: LocalDate, rightLimit: LocalDate) -> Unit,
    clearPredicate: () -> Unit,
) {
    val viewModel by remember { mutableStateOf(DateFilterDialogViewModel()) }
    Column(
        Modifier.fillMaxSize().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(internationalization.valueShouldBeGreaterThan, Modifier.fillMaxWidth(0.4f))
            DateTextInputField(
                viewModel.lowerThanField,
                viewModel.lowerThanError,
            ) { viewModel.lowerThanField = it }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(internationalization.valueShouldBeLessThan, Modifier.fillMaxWidth(0.4f))
            DateTextInputField(
                viewModel.greaterThanField,
                viewModel.greaterThanError,
            ) { viewModel.greaterThanField = it }
        }
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = {
                if (
                    !viewModel.validateLowerField() or
                    !viewModel.validateGreaterField()
                ) {
                    return@Button
                }
                val start = parseDate(viewModel.greaterThanField.text)
                val end = parseDate(viewModel.lowerThanField.text)
                setFilterPredicate(start, end)
            }) {
                Text(internationalization.confirm)
            }

            TextButton(onClick = {
                clearPredicate()
            }) {
                Text(internationalization.cancel)
            }
        }
    }
}


@Composable
@Preview
fun DateFilterDialogContentPreview() {
    DateFilterDialogContent(PropertiesInternationalizationProvider().chosen.dateFilterDialog, { _, _ -> }, {})
}
