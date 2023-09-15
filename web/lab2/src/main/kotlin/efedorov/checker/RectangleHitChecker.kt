package efedorov.checker

import efedorov.model.Point

class RectangleHitChecker: HitChecker {
    override fun test(t: Point): Boolean {
        val (x, y, r) = t
        if(x <= 0 && y >= 0) {
            return -r <= x && y < r/2
        }
        return false;
    }
}