package lab8.client.presentation.compose.ui.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationScreen
import lab8.entities.user.User
import java.util.*

@Composable
fun LoginAppBar(
    localization: Internationalization.LoginAppBar,
    supportedLocales: Iterable<Locale>,
    changeLocale: (Locale) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    TopAppBar(
//        title= { Text("Лаба)") }
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navigator.push(InternationalizationScreen(supportedLocales, changeLocale))
            }
        ) {
            Text(localization.changeLanguage)
        }
    }
}

@Composable
fun CurrentUserText(user: User? = null) {
    Text("${user?.name}#${user?.id}")
}
