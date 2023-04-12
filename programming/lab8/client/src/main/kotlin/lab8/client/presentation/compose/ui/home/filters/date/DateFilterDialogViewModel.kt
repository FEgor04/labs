package lab8.client.presentation.compose.ui.home.filters.date

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import java.time.LocalDate

class DateFilterDialogViewModel : ScreenModel {
    var greaterThanError by mutableStateOf(false);
    var greaterThanField by mutableStateOf(TextFieldValue())
    var lowerThanError by mutableStateOf(false);
    var lowerThanField by mutableStateOf(TextFieldValue())

    fun validateLowerField(): Boolean {
        return try {
            parseDate(lowerThanField.text)
            true
        } catch (e: Exception) {
            lowerThanError = true
            false
        }
    }

    fun validateGreaterField(): Boolean {
        return try {
            parseDate(greaterThanField.text)
            true
        } catch (e: Exception) {
            greaterThanError = true
            false
        }
    }
}

internal fun parseDate(s: String): LocalDate {
    val day = s.substring(0..1).toInt()
    val month = s.substring(2..3).toInt()
    val year = s.substring(4..7).toInt()
    return LocalDate.of(year, month, day)
}
