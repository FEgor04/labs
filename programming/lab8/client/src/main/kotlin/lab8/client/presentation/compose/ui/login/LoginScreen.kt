package lab8.client.presentation.compose.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.runBlocking
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import org.koin.java.KoinJavaComponent.inject
import java.util.*

data class LoginScreen(val getLocalization: () -> Internationalization) : Screen {


    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        LifecycleEffect(
            onStarted = {
                println("Started new LoginScreen!")
            }
        )
        val localization = getLocalization()

        val screenModel = rememberScreenModel { LoginScreenModel() }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .requiredSizeIn(minWidth = 200.dp, minHeight = 200.dp)
                .fillMaxWidth(),
        ) {
            Spacer(Modifier.height(20.dp))
            TextField(
                screenModel.login,
                { screenModel.login = it },
                label = { Text(localization.loginForm.login) },
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
            )
            Spacer(Modifier.height(20.dp))
            TextField(
                screenModel.password,
                { screenModel.password = it },
                label = { Text(localization.loginForm.password) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
            )
            Spacer(Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
            ) {
                Button(
                    { screenModel.handleSignIn() },
                    content = { Text(localization.loginForm.signIn) })
                Button(
                    { screenModel.handleSignUp() },
                    content = { Text(localization.loginForm.signUp) },
                    enabled = false
                )
            }

            if (screenModel.errorDescription.isNotEmpty() && !screenModel.isLoading) {
                Text(
                    screenModel.errorDescription,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                )
            } else if(screenModel.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
