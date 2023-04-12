package lab8.client.presentation.compose.ui.i18n.properties

import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap


class PropertiesInternationalizationProvider: InternationalizationProvider {
    private var internationalizations = mutableMapOf<Locale, Internationalization>();

    init {
        println("Loading localization")
        val localizationFiles: List<String> = getLocalizationFiles();
        for(i in localizationFiles) {
            val curProperties = Properties()
            val curFile = this::class.java.getResourceAsStream(i)
            curProperties.load(curFile)
            val cur = PropertiesInternationalization(curProperties)
            internationalizations[cur.getLocale()] = cur
        }
    }
    override fun getSupportedLocales(): List<Locale> {
        return internationalizations.keys.toList()
    }

    override fun getForLocale(locale: Locale): Internationalization? {
        return internationalizations[locale]
    }

    override var chosen: Internationalization = internationalizations.firstNotNullOf { it.value }

    private fun getLocalizationFiles(): List<String> {
        return arrayListOf(
            "/internationalization/en_CA.properties",
            "/internationalization/ru_RU.properties",
            "/internationalization/tr_TR.properties",
            "/internationalization/lv_LV.properties",
        )
    }

}