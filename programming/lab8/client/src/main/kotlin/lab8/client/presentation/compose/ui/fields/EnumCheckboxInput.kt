package lab8.client.presentation.compose.ui.fields

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import lab8.entities.vehicle.FuelType

@Composable
inline fun <reified E : Enum<E>> EnumCheckboxField(
    isNullable: Boolean,
    values: HashSet<E?>,
    modifier: Modifier = Modifier,
    crossinline onCheck: (E?, Boolean) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        enumValues<E>().sortedBy { it.toString() }.map { value ->
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(value in values, { onCheck(value, it) })
                Text(value.toString())
            }
        }
        if (isNullable) {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    null in values,
                    {
                        onCheck(null, it)
                    }
                )
                Text("null")
            }
        }
    }
}

@Composable
@Preview
fun EnumCheckboxFieldPreview() {
    EnumCheckboxField<FuelType>(true, hashSetOf()) { _, _ -> }
}
