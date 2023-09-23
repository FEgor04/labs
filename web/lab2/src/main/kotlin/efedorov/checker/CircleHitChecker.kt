package efedorov.checker

import efedorov.model.Point
import kotlin.math.abs

class CircleHitChecker: HitChecker {
    private val epsilon = 1e-5;
    override fun test(t: Point): Boolean {
        val (x, y, r) = t
        if(x >= 0 && y <= 0) {
            return ((x * x) + (y * y) - r) <= epsilon;
        }
        return false;
    }
}