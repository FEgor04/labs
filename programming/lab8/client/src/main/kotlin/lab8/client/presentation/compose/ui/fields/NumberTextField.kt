package lab8.client.presentation.compose.ui.fields

import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun <T: Number> NumberTextField(
    value: TextFieldValue,
    minValue: T,
    maxValue: T,
    caster: (String) -> T,
    label: @Composable (() -> Unit)? = null,
    isIntegerOnly: Boolean = false,
    isPositiveOnly: Boolean = false,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onChange: (TextFieldValue) -> Unit,
) {
    val a: Int
    val acceptableCharacters = mutableSetOf<Char>()
    acceptableCharacters.addAll((0..9).map { it.digitToChar() })
    if(!isIntegerOnly) {
        acceptableCharacters.addAll(listOf(',', '.'))
    }
    if(!isPositiveOnly) {
        acceptableCharacters.add('-')
    }
    OutlinedTextField(value, {
        if(it.text.isEmpty()) {
            onChange(it)
            return@OutlinedTextField
        }
        if (it.text.all { it in acceptableCharacters }) {
            val itNumber = try {
                caster(it.text)
            } catch(e: IllegalArgumentException) {
                return@OutlinedTextField
            }
            if(itNumber.toDouble() in (minValue.toDouble()..maxValue.toDouble())) {
                onChange(it)
            }
        }
    }, label = label, modifier=modifier, enabled=enabled)
}
