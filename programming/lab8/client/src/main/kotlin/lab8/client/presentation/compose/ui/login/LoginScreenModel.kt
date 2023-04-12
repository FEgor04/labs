package lab8.client.presentation.compose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lab8.client.domain.RemoteCommandHandler
import lab8.client.exceptions.ClientException
import lab8.entities.user.User
import org.koin.java.KoinJavaComponent.inject
import java.rmi.ServerException
import kotlin.concurrent.thread

class LoginScreenModel(
    val setCurrentUser: (User) -> Unit,
    val goToHomeScreen: () -> Unit,
) : ScreenModel {
    var login by mutableStateOf(TextFieldValue("test"))
    var password by mutableStateOf(TextFieldValue("test"))
    var isLoading by mutableStateOf(false)
    var hidePassword by mutableStateOf(true)
    private val service by inject<RemoteCommandHandler>(RemoteCommandHandler::class.java)
    var errorDescription by mutableStateOf("")

    suspend fun handleSignIn() {
        isLoading = true
        try {
            val user = withContext(Dispatchers.IO) { service.tryToLogin(User(login.text, password.text, -1)) }
            setCurrentUser(user)
            goToHomeScreen()
        }
        catch (e: Exception) {
            errorDescription = e.toString()
        }
        finally {
            isLoading = false
        }
    }

    suspend fun handleSignUp() {
        isLoading = true
        try {
            val user = withContext(Dispatchers.IO) { service.createUser(User(login.text, password.text, -1)) }
            setCurrentUser(user)
            goToHomeScreen()
        }
        catch (e: Exception) {
            errorDescription = e.toString()
        }
        finally {
            isLoading = false
        }
    }
}
