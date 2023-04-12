package lab8.client.presentation.compose.ui.home.filters.enum

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EnumFilterDialogViewModel<E : Enum<E>> {
    var selectedValues by mutableStateOf(HashSet<E?>())
        private set

    fun checkValue(value: E?, isChecked: Boolean) {
        val newValues = HashSet<E?>()
        newValues.addAll(selectedValues)
        if(isChecked) {
            newValues.add(value)
        }
        else {
            newValues.remove(value)
        }
        selectedValues = newValues
    }

}