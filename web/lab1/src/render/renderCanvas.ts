import {Point} from "../model.ts";

export function renderCanvas(r: number, point?: Point | undefined) {
    const canvasElement = document.querySelector<HTMLCanvasElement>("#canvas")!
    const size = 300
    canvasElement.setAttribute("width", size.toString())
    canvasElement.setAttribute("height", size.toString())
    const ctx = canvasElement.getContext("2d")!
    drawPolygon(ctx, size, r)
    drawText(ctx, size, r)
    drawAxes(ctx, size)
    point && drawPoint(ctx, size, point)
}

/**
 * Рисует оси координат
 * @param ctx контекст рисования
 * @param size размер канваса
 */
function drawAxes(ctx: CanvasRenderingContext2D, size: number) {
    ctx.fillStyle = "black"
    ctx.fillRect(0, size / 2, size, 1)
    ctx.fillRect(size / 2, 0, 1, size)
    ctx.beginPath()
    ctx.arc(size / 2, size/ 2, 3, 0, 2 * Math.PI, false)
    ctx.fill()
}

/**
 * Рисует область из задания
 * @param ctx контекст рисования
 * @param size размер канваса
 * @param r радиус
 */
function drawPolygon(ctx: CanvasRenderingContext2D, size: number, r: number) {
    let totalPoints = 12;
    let pointInPixels = size / totalPoints
    ctx.fillStyle = "lightblue"
    ctx.beginPath()
    ctx.moveTo(size / 2, size / 2)
    ctx.lineTo(size / 2, size / 2 - r * pointInPixels)
    ctx.lineTo(size / 2 + r / 2 * pointInPixels, size / 2)
    ctx.fill()

    ctx.beginPath()
    ctx.arc(size / 2, size /2, r / 2 * pointInPixels, Math.PI, 3 * Math.PI /2)
    ctx.moveTo(size / 2, size / 2 - r / 2 * pointInPixels)
    ctx.lineTo(size / 2, size / 2)
    ctx.lineTo(size / 2 - r / 2 * pointInPixels, size / 2)
    ctx.fill()

    ctx.beginPath()
    ctx.moveTo(size / 2, size / 2)
    ctx.lineTo(size / 2 - r / 2 * pointInPixels, size / 2)
    ctx.lineTo(size / 2 - r / 2 * pointInPixels, size / 2 + r * pointInPixels)
    ctx.lineTo(size / 2, size / 2 + r * pointInPixels)
    ctx.fill()
}


/**
 * Рисует подписи к осям
 * @param ctx контекст рисования
 * @param size размер канваса
 * @param r радиус
 */
function drawText(ctx: CanvasRenderingContext2D, size: number, r: number) {
    ctx.fillStyle = "black"
    let totalPoints = 12;
    let pointInPixels = size / totalPoints

    for(let sign = -1; sign <= 1; sign += 2) {
        ctx.fillText("R/2",size / 2 + sign * pointInPixels * r / 2, size / 2)
        ctx.fillText("R/2",size / 2, size / 2 + sign * pointInPixels * r / 2)
        ctx.fillText("R",size / 2 + sign * pointInPixels * r , size / 2)
        ctx.fillText("R",size / 2, size / 2 + sign * pointInPixels * r)
    }

}

/**
 * Рисует точку на канвасе
 * @param ctx контекст
 * @param size размер канваса
 * @param point координаты точки
 */
function drawPoint(ctx: CanvasRenderingContext2D, size: number, point: Point) {
    ctx.fillStyle = "#00386e"
    let totalPoints = 12;
    let pointInPixels = size / totalPoints
    ctx.beginPath()
    ctx.arc(size / 2 + pointInPixels * point.x, size / 2 - point.y * pointInPixels, 5, 0, Math.PI * 2)
    ctx.fill();

}
