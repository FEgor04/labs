package lab8.client.presentation.compose.ui.fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import lab8.client.presentation.compose.ui.home.filters.date.DateTransformation

@Composable
fun DateTextInputField(field: TextFieldValue, isError: Boolean, onChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = field,
        isError = isError,
        visualTransformation = DateTransformation(),
        onValueChange = { if (checkDateInput(it.text)) { onChange(it) } },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = { Text("DD.MM.YYYY") },
    )
}

internal fun checkDateInput(str: String): Boolean {
    if (str.isEmpty()) {
        return true
    }
    if (!str.all { it.isDigit() }) {
        return false
    }
    if (str.length <= 2) {
        return str.toInt() <= 31
    }
    if (str.length <= 4) {
        return str.slice(0..1).toInt() <= 31 && str.slice(2 until str.length).toInt() <= 12
    }

    return str.length <= 8 && (str.slice(0..1).toInt() <= 31 && str.slice(2..3).toInt() <= 12)
}
