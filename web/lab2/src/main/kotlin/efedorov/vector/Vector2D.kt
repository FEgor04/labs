package efedorov.vector

import java.awt.Point

data class Vector2D(val x: Double, val y: Double) {

    operator fun plus(b: Vector2D): Vector2D {
        return Vector2D(x + b.x, y + b.y)
    }

    operator fun minus(b: Vector2D): Vector2D {
        return Vector2D(x - b.x, y - b.y)
    }

    operator fun unaryMinus() = Vector2D(-x, -y)

    /**
     * Косое произведение векторов
     * @see https://ru.wikipedia.org/wiki/%D0%9F%D1%81%D0%B5%D0%B2%D0%B4%D0%BE%D1%81%D0%BA%D0%B0%D0%BB%D1%8F%D1%80%D0%BD%D0%BE%D0%B5_%D0%BF%D1%80%D0%BE%D0%B8%D0%B7%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B5
     */
    infix fun skew(b: Vector2D): Double {
        return x * b.y - y * b.x
    }
}