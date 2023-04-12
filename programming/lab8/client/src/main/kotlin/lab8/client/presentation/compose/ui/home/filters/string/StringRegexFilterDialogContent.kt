package lab8.client.presentation.compose.ui.home.filters.string

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider

@Composable
fun StringRegexFilterDialogContent(
    i18n: Internationalization.StringRegexFilterDialog,
    setFilterPredicate: (String) -> Unit,
    clearPredicate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var field by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            field,
            { field = it },
            modifier = Modifier.fillMaxWidth(0.8f),
            label = { Text(i18n.stringShouldMatchRegex) })

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button({setFilterPredicate(field.text)}) {
                Text(i18n.confirm)
            }

            TextButton({clearPredicate()}) {
                Text(i18n.cancel)
            }
        }
    }
}

@Composable
@Preview
fun StringRegexFilterDialogPreview() {
    StringRegexFilterDialogContent(
        PropertiesInternationalizationProvider().chosen.stringDialog, {}, {}, Modifier
            .fillMaxWidth(1f)
            .padding(16.dp)
    )
}
