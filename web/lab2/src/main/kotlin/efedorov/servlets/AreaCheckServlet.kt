package efedorov.servlets

import efedorov.checker.CircleHitChecker
import efedorov.checker.HitChecker
import efedorov.checker.RectangleHitChecker
import efedorov.checker.TriangleHitChecker
import efedorov.model.HistoryEntry
import efedorov.model.store.HistoryManager
import efedorov.model.store.HistoryManagerProvider
import efedorov.model.Point
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.lang.NumberFormatException
import java.time.Duration
import java.time.Instant

class AreaCheckServlet : HttpServlet() {
    private val historyManager: HistoryManager = HistoryManagerProvider.getInstance()
    private val hitCheckers: List<HitChecker> = listOf(
        RectangleHitChecker(),
        TriangleHitChecker(),
        CircleHitChecker(),
    )

    private fun checkParameter(
        parameterName: String,
        allowedRange: ClosedRange<Double>,
        req: HttpServletRequest,
        resp: HttpServletResponse
    ): Double? {
        val parameterValue: String? = req.getParameter(parameterName)
        if (parameterValue == null) {
            resp.status = 400
            resp.writer.println("Bad request. Parameter $parameterName is not specified.")
            return null
        }
        val parsedValue: Double;
        try {
            parsedValue = parameterValue.toDouble()
        } catch (e: NumberFormatException) {
            resp.status = 400
            resp.writer.println("Bad request. Parameter $parameterName is not a number.")
            return null
        }

        if (parsedValue !in allowedRange) {
            resp.status = 400
            resp.writer.println("Bad request. Parameter $parameterName is not in allowed range: $allowedRange")
            return null
        }

        return parsedValue
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val session = req.getSession(true)

        val x = checkParameter("x", -3.0..5.0, req, resp)
        val y = checkParameter("y", -3.0..5.0, req, resp)
        val r = checkParameter("r", 1.0..3.0, req, resp)

        if (x == null || y == null || r == null) {
            return
        }

        val checkStartTime = Instant.now()
        // TODO: нормальная логика проверки
        val point = Point(x, y, r)
        val success = this.hitCheckers.any { it.test(point) }
        val checkEndTime = Instant.now()
        val executionTime = Duration.between(checkStartTime, checkEndTime)

        val historyEntry = HistoryEntry(
            point,
            success,
            checkEndTime,
            executionTime
        )

        historyManager.pushEntry(historyEntry, session)

        val dispatcher = req.getRequestDispatcher("results.jsp")
        dispatcher.forward(req, resp)
    }
}