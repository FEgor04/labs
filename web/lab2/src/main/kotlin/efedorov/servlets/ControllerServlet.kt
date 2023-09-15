package efedorov.servlets

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class ControllerServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val dispatcher = req.getRequestDispatcher("/check")
        dispatcher.forward(req, resp)
    }
}