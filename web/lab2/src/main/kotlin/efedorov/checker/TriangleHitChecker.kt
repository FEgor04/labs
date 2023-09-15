package efedorov.checker

import efedorov.model.Point
import efedorov.vector.Vector2D
import kotlin.math.abs

class TriangleHitChecker : HitChecker {
    override fun test(t: Point): Boolean {
        val (x, y, r) = t
        if (x >= 0 && y >= 0) {
            val a = Vector2D(0.0, 0.0)
            val b = Vector2D(0.0, r)
            val c = Vector2D(r, 0.0)
            val p = Vector2D(x, y)

            val s1 = ((p - a) skew (p - b))
            val s2 = ((p - b) skew (p - c))
            val s3 = ((p - c) skew (p - a))

            return (s1 >= 0 && s2 >= 0 && s3 >= 0) || (s1 <= 0 && s2 <= 0 && s3 <= 0)
        }
        return false;
    }
}