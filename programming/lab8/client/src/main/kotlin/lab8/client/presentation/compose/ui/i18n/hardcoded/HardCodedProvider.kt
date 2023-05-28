package lab8.client.presentation.compose.ui.i18n.hardcoded

import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import java.util.*

class HardCodedProvider: InternationalizationProvider {
    private val i18ns = hashMapOf<Locale, Internationalization>(
        Locale.forLanguageTag("RU") to HardCodedRuLocale(),
        Locale.CANADA to HardCodedEnLocale(),
    )

    override var chosen: Internationalization = i18ns.values.first()

    override fun getForLocale(locale: Locale): Internationalization? {
        println("Returned ${locale} internationalization")
        return i18ns[locale]
    }

    override fun getSupportedLocales(): List<Locale> = i18ns.keys.toList()
}