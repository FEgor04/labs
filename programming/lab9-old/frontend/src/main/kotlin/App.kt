import layout.Layout
import pages.HomePage
import pages.LoginPage
import react.FC
import react.Props
import react.create
import react.router.PathRoute
import react.router.Routes
import react.router.dom.BrowserRouter

private const val LOGIN_PATH = "/login"

public val App = FC<Props> {
    BrowserRouter {
        Layout {
            Routes {
                PathRoute {
                    this.path = "/"
                    this.element = HomePage.create()
                }
                PathRoute {
                    this.path = "/login"
                    this.element = LoginPage.create()
                }
            }
        }
    }
}