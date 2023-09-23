package efedorov.checker

import efedorov.model.Point

class RectangleHitChecker: HitChecker {
    private val epsilon = 1e-5;
    override fun test(t: Point): Boolean {
        val (x, y, r) = t
        if(x <= 0 && y >= 0) {
            return (-r - x) <= epsilon && y - r/2 <= epsilon
        }
        return false;
    }
}