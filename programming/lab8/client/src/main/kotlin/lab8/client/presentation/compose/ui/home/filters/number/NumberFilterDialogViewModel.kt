package lab8.client.presentation.compose.ui.home.filters.number

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class NumberFilterDialogViewModel() {
    var lowerBorder by mutableStateOf(TextFieldValue())
    var upperBorder by mutableStateOf(TextFieldValue())

    fun lowerBorderAsFloat(): Float {
        return lowerBorder.text.toFloatOrNull() ?: Float.MIN_VALUE
    }

    fun upperBorderAsFloat(): Float {
        return upperBorder.text.toFloatOrNull() ?: Float.MAX_VALUE
    }
}