package com.efedorov.lab3.checkers

import com.efedorov.lab3.model.Point

class RectangleHitChecker: HitChecker {
    override fun check(point: Point): Boolean {
        val (x, y, r) = point
        return x in (-r/2..0.0) && y in (0.0..r)
    }
}