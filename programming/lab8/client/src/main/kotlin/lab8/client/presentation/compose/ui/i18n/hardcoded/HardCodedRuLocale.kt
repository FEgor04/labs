package lab8.client.presentation.compose.ui.i18n.hardcoded

import lab8.client.presentation.compose.ui.i18n.Internationalization
import java.util.*

class HardCodedRuLocale: Internationalization {
    override fun getLocale() = Locale.forLanguageTag("RU")

    override val loginForm = object : Internationalization.LoginForm {
            override val login: String = "Логин"
            override val password: String = "Пароль"
            override val signIn: String = "Войти"
            override val signUp: String = "Зарегистрироваться"
        }
    override val appBar: Internationalization.AppBar = object: Internationalization.AppBar {
        override val changeLanguage: String = "Язык"

    }
}