package lab8.client.presentation.compose.ui.i18n

import java.util.*

interface InternationalizationProvider {
    fun getSupportedLocales(): List<Locale>
    fun getForLocale(locale: Locale): Internationalization?
    var chosen: Internationalization
}