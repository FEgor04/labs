
const CANVAS_SIZE = document.getElementById("canvas").offsetWidth
const MAX_RADIUS = 5
const TOTAL_CANVAS_POINTS = (MAX_RADIUS + 1) * 2;
const POINT_IN_PIXELS = CANVAS_SIZE / TOTAL_CANVAS_POINTS;

function renderCanvas(r, points) {
    const canvasElement = document.getElementById("canvas")
    canvasElement.width = CANVAS_SIZE;
    canvasElement.height = CANVAS_SIZE;
    const ctx = canvasElement.getContext("2d")

    ctx.fillStyle = "#f8fafc"
    ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    drawPolygon(ctx, r);
    drawGrid(ctx)
    drawText(ctx, r);
    drawAxes(ctx);
    points
        .filter(point => {
            return point.r === r
        })
        .forEach(point => {
            drawPoint(ctx, point);
        })
}

/**
 * Рисует оси координат
 * @param ctx контекст рисования
 */
function drawAxes(ctx) {
    ctx.fillStyle = "black";
    ctx.fillRect(0, CANVAS_SIZE / 2, CANVAS_SIZE, 1);
    ctx.fillRect(CANVAS_SIZE / 2, 0, 1, CANVAS_SIZE);
    ctx.beginPath();
    ctx.arc(CANVAS_SIZE / 2, CANVAS_SIZE / 2, 3, 0, 2 * Math.PI, false);
    ctx.fill();
}

function drawGrid(ctx) {
    ctx.strokeStyle = "grey";
    let minRadius = -5;
    let maxRadius = 5;
    let step = 1
    for (let i = minRadius; i <= maxRadius; i += step) {
        ctx.beginPath();
        ctx.moveTo(0, CANVAS_SIZE / 2 + i * POINT_IN_PIXELS)
        ctx.lineTo(CANVAS_SIZE, CANVAS_SIZE / 2 + i * POINT_IN_PIXELS)
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(CANVAS_SIZE / 2 + i * POINT_IN_PIXELS, 0)
        ctx.lineTo(CANVAS_SIZE / 2 + i * POINT_IN_PIXELS, CANVAS_SIZE)
        ctx.stroke();
    }
}

/**
 * Рисует область из задания
 * @param {CanvasRenderingContext2D} ctx контекст рисования
 * @param {number} r радиус
 */
function drawPolygon(ctx, r) {
    if (!r) {
        return;
    }
    ctx.fillStyle = "lightblue";
    ctx.beginPath()
    ctx.moveTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2 - r * POINT_IN_PIXELS)
    ctx.lineTo(CANVAS_SIZE / 2 - r / 2 * POINT_IN_PIXELS, CANVAS_SIZE / 2 - r * POINT_IN_PIXELS)
    ctx.lineTo(CANVAS_SIZE / 2 - r / 2 * POINT_IN_PIXELS, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2 - r * POINT_IN_PIXELS, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2 + POINT_IN_PIXELS * r / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2 + r / 2 * POINT_IN_PIXELS, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2 - POINT_IN_PIXELS * r / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2)
    ctx.arc(CANVAS_SIZE / 2 , CANVAS_SIZE / 2, r / 2 * POINT_IN_PIXELS, 3 * Math.PI /2 , 2 * Math.PI, );
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2)
    ctx.fill()
    ctx.beginPath()
    ctx.moveTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2 + r / 2 * POINT_IN_PIXELS, CANVAS_SIZE / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2 - POINT_IN_PIXELS * r / 2)
    ctx.lineTo(CANVAS_SIZE / 2, CANVAS_SIZE / 2)
    ctx.fill()
}

/**
 * Рисует подписи к осям
 * @param {CanvasRenderingContext2D} ctx контекст рисования
 * @param r радиус
 */
function drawText(ctx, r) {
    if (!r) {
        return;
    }
    ctx.fillStyle = "black";
    for (let sign = -1; sign <= 1; sign += 2) {
        ctx.fillText("R/2", CANVAS_SIZE / 2 + (sign * POINT_IN_PIXELS * r) / 2, CANVAS_SIZE / 2);
        ctx.fillText("R/2", CANVAS_SIZE / 2, CANVAS_SIZE / 2 + (sign * POINT_IN_PIXELS * r) / 2);
        ctx.fillText("R", CANVAS_SIZE / 2 + sign * POINT_IN_PIXELS * r, CANVAS_SIZE / 2);
        ctx.fillText("R", CANVAS_SIZE / 2, CANVAS_SIZE / 2 + sign * POINT_IN_PIXELS * r);
    }
}

/**
 * Рисует точку на канвасе
 * @param ctx контекст
 * @param point координаты точки
 */
function drawPoint(ctx, point) {
    const colorGreen600 = "#16a34a";
    const colorRed600 = "#dc2626";
    const colorBlue600 = "#2563eb";
    ctx.fillStyle = point.hit ? colorGreen600 : colorRed600;
    if (point.hit == undefined || point.hit == null) {
        ctx.fillStyle = colorBlue600
    }
    ctx.beginPath();
    ctx.arc(
        CANVAS_SIZE / 2 + POINT_IN_PIXELS * point.x,
        CANVAS_SIZE / 2 - point.y * POINT_IN_PIXELS,
        5,
        0,
        Math.PI * 2,
    );
    ctx.fill();
}
