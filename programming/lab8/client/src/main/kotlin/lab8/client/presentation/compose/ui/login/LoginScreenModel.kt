package lab8.client.presentation.compose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.withContext
import lab8.client.domain.RemoteCommandHandler
import lab8.client.exceptions.ClientException
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import lab8.entities.user.User
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import kotlin.concurrent.thread

class LoginScreenModel : ScreenModel {
    var login by mutableStateOf(TextFieldValue())
    var password by mutableStateOf(TextFieldValue())
    var isLoading by mutableStateOf(false)
    private val service by inject<RemoteCommandHandler>(RemoteCommandHandler::class.java)
    var errorDescription by mutableStateOf("")

    fun handleSignIn() {
        var wasException = false
        thread(true) {
            try {
                isLoading = true
                service.tryToLogin(
                    User(
                        name = login.text,
                        password = password.text,
                        id = -1,
                    )
                )
            } catch (e: ClientException) {
                errorDescription = e.toString()
                wasException = true
            } finally {
                isLoading = false
            }
            if (!wasException) {
                errorDescription = "Успех!"
            }

        }
    }

    fun handleSignUp() {

    }
}