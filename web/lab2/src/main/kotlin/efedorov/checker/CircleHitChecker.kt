package efedorov.checker

import efedorov.model.Point

class CircleHitChecker: HitChecker {
    override fun test(t: Point): Boolean {
        val (x, y, r) = t
        if(x >= 0 && y <= 0) {
            return (x * x) + (y * y) <= r;
        }
        return false;
    }
}