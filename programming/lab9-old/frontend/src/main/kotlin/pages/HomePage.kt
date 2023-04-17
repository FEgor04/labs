package pages

import csstype.PaddingTop
import emotion.react.css
import mui.material.Button
import mui.material.FormControl
import mui.material.TextField
import react.FC
import react.Fragment
import react.Props
import react.create
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.useState
import web.html.InputType

val HomePage = FC<Props> {
    div {
        h1 {
            +"Hello"
        }
    }
}