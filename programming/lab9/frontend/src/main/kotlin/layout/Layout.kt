package layout

import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.PropsWithChildren
import react.dom.html.ReactHTML.div

external interface LayoutProps : PropsWithChildren {
}

val Layout = FC<LayoutProps> {
    Header {
    }
    +it.children
}