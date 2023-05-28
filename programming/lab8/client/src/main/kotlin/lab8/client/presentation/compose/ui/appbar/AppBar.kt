package lab8.client.presentation.compose.ui.appbar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role.Companion.Button
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import lab8.client.presentation.compose.ui.i18n.InternationalizationScreen
import lab8.client.presentation.compose.ui.i18n.hardcoded.HardCodedProvider
import lab8.logger.KCoolLogger
import org.koin.java.KoinJavaComponent.inject
import java.util.*

@Composable
fun AppBar(localization: Internationalization.AppBar, getSupportedLocales: () -> Iterable<Locale>, changeLocale: (Locale) -> Unit) {
    val navigator = LocalNavigator.currentOrThrow

    TopAppBar(
//        title= { Text("Лаба)") }
    ) {
        Button(
            onClick = {
                navigator.push(InternationalizationScreen(getSupportedLocales, changeLocale))
            }
        ) {
            Text(localization.changeLanguage)
        }
    }
}