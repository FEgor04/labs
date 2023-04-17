import react.Fragment
import react.create
import react.dom.client.createRoot
import web.dom.document


fun main() {

    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    val root = createRoot(container)
    root.render(
        Fragment.create {
            App {
            }
        }
    )
}