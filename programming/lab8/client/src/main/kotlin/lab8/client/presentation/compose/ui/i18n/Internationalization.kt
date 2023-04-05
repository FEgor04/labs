package lab8.client.presentation.compose.ui.i18n

import java.util.*

interface Internationalization {
    fun getLocale(): Locale
    val loginForm: LoginForm
    val appBar: AppBar
    interface LoginForm {
        val login: String
        val password: String
        val signIn: String
        val signUp: String
    }

    interface AppBar {
        val changeLanguage: String
    }
}