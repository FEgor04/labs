package lab8.client.presentation.compose.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import lab8.client.presentation.compose.ui.home.HomeScreen
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import lab8.entities.user.User

data class LoginScreen(
    val provider: InternationalizationProvider,
    val currentUser: User?,
    val setCurrentUser: (User) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        LifecycleEffect(
            onStarted = {
                println("Started new LoginScreen!")
            }
        )
        var localization by mutableStateOf(provider.chosen)
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel {
            LoginScreenModel(setCurrentUser) {
                navigator.push(HomeScreen(provider))
            }
        }
        Scaffold(
            topBar = {
                LoginAppBar(
                    localization.loginAppBar,
                    provider.getSupportedLocales()
                ) { locale ->
                    provider.chosen = provider.getForLocale(locale)!!
                    localization = provider.chosen
                    println("Changed locale to $locale")
                }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .requiredSizeIn(minWidth = 200.dp, minHeight = 200.dp)
                    .fillMaxWidth(),
            ) {
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    screenModel.login,
                    { screenModel.login = it },
                    label = { Text(localization.loginForm.login) },
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    screenModel.password,
                    { screenModel.password = it },
                    label = { Text(localization.loginForm.password) },
                    visualTransformation = if (screenModel.hidePassword) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    trailingIcon = {
                                   IconButton({screenModel.hidePassword = !screenModel.hidePassword}) {
                                       val icon = if(screenModel.hidePassword) {
                                           Icons.Filled.VisibilityOff
                                       } else {
                                           Icons.Filled.Visibility
                                       }
                                       Icon(icon, "no desc")
                                   }
                    },
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                ) {
                    Button(
                        { coroutineScope.launch { screenModel.handleSignIn() } },
                        content = { Text(localization.loginForm.signIn) }
                    )
                    Button(
                        { coroutineScope.launch { screenModel.handleSignUp() } },
                        content = { Text(localization.loginForm.signUp) },
                    )
                }

                if (screenModel.errorDescription.isNotEmpty() && !screenModel.isLoading) {
                    Text(
                        screenModel.errorDescription,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    )
                } else if (screenModel.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
