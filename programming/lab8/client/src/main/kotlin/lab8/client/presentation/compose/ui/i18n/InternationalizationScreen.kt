package lab8.client.presentation.compose.ui.i18n

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import java.util.*

data class InternationalizationScreen(
    val supportedLocales: Iterable<Locale>,
    val changeLocale: (Locale) -> Unit
) : Screen {
    @Preview
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            supportedLocales.map {
                Button(
                    onClick = {
                        changeLocale(it)
                        navigator.pop()
                    },
                    content = { Text(it.displayName) },
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
        }
    }
}
