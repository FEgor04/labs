package pages

import csstype.Display
import csstype.JustifyContent
import csstype.px
import emotion.react.css
import exceptions.LoginException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.material.Button
import mui.material.FormControl
import mui.material.FormControlMargin
import mui.material.TextField
import react.FC
import react.Fragment
import react.Props
import react.create
import react.dom.events.ChangeEventHandler
import react.dom.html.ReactHTML.div
import react.dom.onChange
import react.useState
import services.UserService
import web.html.HTMLInputElement
import web.html.InputType
import web.prompts.alert

external interface LoginProps : Props {

}

val LoginPage = FC<LoginProps> {
    val scope = MainScope()
    var showPassword: Boolean by useState(false)
    var username: String by useState("")
    var password: String by useState("")

    val changeUsernameHandler: ChangeEventHandler<HTMLInputElement> = { username = it.target.value }
    val changePasswordHandler: ChangeEventHandler<HTMLInputElement> = { password = it.target.value }

    div {
        css {
            marginTop = 50.px
            display = Display.flex
            justifyContent = JustifyContent.center
        }
        FormControl {
            margin = FormControlMargin.normal
            TextField {
                css {
                    marginTop = 20.px
                }
                id = "username"
                label = Fragment.create {
                    +"Username"
                }
                margin = FormControlMargin.normal
                value = username
                onChange = changeUsernameHandler.asDynamic()
                required
                type = InputType.text
            }

            TextField {
                css {
                    marginTop = 20.px
                }
                id = "password"
                label = Fragment.create {
                    +"Password"
                }
                required
                value = password
                type = if (showPassword) {
                    InputType.text
                } else {
                    InputType.password
                }
                onChange = changePasswordHandler.asDynamic()
            }

            Button {
                css {
                    marginTop = 20.px
                }
                children = Fragment.create() {
                    +"Log In"
                }
                onClick = {

                    scope.launch {
                        try {
                            val loginResult = UserService().tryToLogin(username, password)
                            alert(UserService().getMe().toString())
                        } catch (e: LoginException) {
                            alert(e.toString())
                        }

                    }
                }
            }
        }
    }
}