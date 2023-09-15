<%--
  Created by IntelliJ IDEA.
  User: fegor04
  Date: 17.09.2023
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<canvas width="500" height="500" id="canvas"
        class="bg-slate-50 rounded-md shadow-md border border-slate-200"></canvas>
<script>

    const CANVAS_SIZE = 500;
    const MAX_RADIUS = 3
    const TOTAL_CANVAS_POINTS = (MAX_RADIUS + 1) * 2;
    const POINT_IN_PIXELS = CANVAS_SIZE / TOTAL_CANVAS_POINTS;

    function renderCanvas(r, points) {
        const canvasElement = document.getElementById("canvas")
        canvasElement.setAttribute("width", CANVAS_SIZE.toString());
        canvasElement.setAttribute("height", CANVAS_SIZE.toString());
        const ctx = canvasElement.getContext("2d")
        drawPolygon(ctx, CANVAS_SIZE, r);
        drawGrid(ctx, CANVAS_SIZE)
        drawText(ctx, CANVAS_SIZE, r);
        drawAxes(ctx, CANVAS_SIZE);
        points
            .filter(point => {
                return point.r === r
            })
            .forEach(point => {
                drawPoint(ctx, CANVAS_SIZE, point);
            })
    }

    /**
     * Рисует оси координат
     * @param ctx контекст рисования
     * @param size размер канваса
     */
    function drawAxes(ctx, size) {
        ctx.fillStyle = "black";
        ctx.fillRect(0, size / 2, size, 1);
        ctx.fillRect(size / 2, 0, 1, size);
        ctx.beginPath();
        ctx.arc(size / 2, size / 2, 3, 0, 2 * Math.PI, false);
        ctx.fill();
    }

    function drawGrid(ctx, size) {
        ctx.strokeStyle = "grey";
        let minRadius = -3;
        let maxRadius = 3;
        let step = 1
        for(let i = minRadius; i <= maxRadius; i += step) {
            ctx.beginPath();
            ctx.moveTo(0, size / 2 + i * POINT_IN_PIXELS)
            ctx.lineTo(size, size / 2 + i * POINT_IN_PIXELS)
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(size / 2 + i * POINT_IN_PIXELS, 0)
            ctx.lineTo(size / 2 + i * POINT_IN_PIXELS, size)
            ctx.stroke();
        }
    }

    /**
     * Рисует область из задания
     * @param {CanvasRenderingContext2D} ctx контекст рисования
     * @param {number} size размер канваса
     * @param {number} r радиус
     */
    function drawPolygon(ctx, size, r) {
        ctx.fillStyle = "lightblue";
        ctx.beginPath()
        ctx.moveTo(size / 2 + POINT_IN_PIXELS * r, size / 2)
        ctx.lineTo(size / 2, size / 2 - POINT_IN_PIXELS * r)
        ctx.lineTo(size / 2, size / 2 - POINT_IN_PIXELS * r / 2)
        ctx.lineTo(size / 2 - POINT_IN_PIXELS * r, size / 2 - POINT_IN_PIXELS * r / 2)
        ctx.lineTo(size / 2 - POINT_IN_PIXELS * r, size / 2)
        ctx.lineTo(size / 2, size / 2)
        ctx.arc(size / 2, size / 2, POINT_IN_PIXELS * r / 2, 0, Math.PI / 2)
        ctx.lineTo(size / 2, size / 2)
        ctx.fill()
    }

    /**
     * Рисует подписи к осям
     * @param ctx контекст рисования
     * @param size размер канваса
     * @param r радиус
     */
    function drawText(ctx, size, r) {
        ctx.fillStyle = "black";

        for (let sign = -1; sign <= 1; sign += 2) {
            ctx.fillText("R/2", size / 2 + (sign * POINT_IN_PIXELS * r) / 2, size / 2);
            ctx.fillText("R/2", size / 2, size / 2 + (sign * POINT_IN_PIXELS * r) / 2);
            ctx.fillText("R", size / 2 + sign * POINT_IN_PIXELS * r, size / 2);
            ctx.fillText("R", size / 2, size / 2 + sign * POINT_IN_PIXELS * r);
        }
    }

    /**
     * Рисует точку на канвасе
     * @param ctx контекст
     * @param size размер канваса
     * @param point координаты точки
     */
    function drawPoint(ctx, size, point) {
        const colorGreen600 = "#16a34a";
        const colorRed600 = "#dc2626";
        const colorBlue600 = "#2563eb";
        ctx.fillStyle = point.success ? colorGreen600 : colorRed600;
        if(point.success == undefined || point.success == null) {
            ctx.fillStyle = colorBlue600
        }
        ctx.beginPath();
        ctx.arc(
            size / 2 + POINT_IN_PIXELS * point.x,
            size / 2 - point.y * POINT_IN_PIXELS,
            5,
            0,
            Math.PI * 2,
        );
        ctx.fill();
    }
</script>
