package lab8.client.presentation.compose.ui.appbar

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun LocaleItem(locale: Locale, onClick: () -> Unit) {
    DropdownMenuItem(onClick) {
        Text(locale.displayLanguage)
    }
}