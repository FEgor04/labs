package lab8.client.presentation.compose.ui.i18n.hardcoded

import lab8.client.presentation.compose.ui.i18n.Internationalization
import java.util.*

class HardCodedEnLocale: Internationalization {
    override fun getLocale(): Locale = Locale.CANADA

    override val loginForm: Internationalization.LoginForm = object : Internationalization.LoginForm {
        override val login: String = "Login"
        override val password: String = "Password"
        override val signIn: String = "Sign In"
        override val signUp: String = "Sign Up"
    }
    override val appBar: Internationalization.AppBar = object : Internationalization.AppBar {
        override val changeLanguage: String = "Language"

    }
}