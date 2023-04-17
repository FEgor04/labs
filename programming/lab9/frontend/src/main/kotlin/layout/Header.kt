package layout

import csstype.Color
import csstype.ColorProperty
import csstype.Cursor
import csstype.Display
import csstype.FontFamily
import csstype.FontWeight
import csstype.LetterSpacing
import csstype.rem
import emotion.react.css
import mui.material.AppBar
import mui.material.AppBarPosition
import mui.material.Button
import mui.material.Container
import mui.material.Toolbar
import mui.material.Typography
import mui.material.TypographyAlign
import mui.system.SxProps
import mui.system.Theme
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML.h6
import react.router.dom.Link
import react.router.dom.NavLink
import react.router.useNavigate

val Header = FC<Props> {
    val navigate = useNavigate()

    AppBar {
        position = AppBarPosition.static
        Container {
            Toolbar {
                disableGutters
                Typography {
                    css {
                        hover {
                            cursor = Cursor.pointer
                        }
                    }
                    sx {
                        fontFamily = FontFamily.monospace
                        fontWeight= FontWeight.bolder
                        letterSpacing=(.3).rem
                        color= Color("inherit")
                    }
                    onClick = { navigate("/") }
                    component = h6
                    +"LAB 9"
                }

                Button {
                    onClick = { navigate("/login") }
                    sx {
                        color = Color("white")
                        display = Display.block
                    }
                    +"Log In"
                }
            }
        }
    }
}