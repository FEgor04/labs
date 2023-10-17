package com.efedorov.lab3.checkers

import com.efedorov.lab3.model.Point
import kotlin.math.absoluteValue

class TriangleHitChecker: HitChecker {
    override fun check(point: Point): Boolean {
        val (x, y, r) = point
        return if(x <= 0 && y <= 0) {
            x.absoluteValue + r * y.absoluteValue < r.absoluteValue
        } else {
            false;
        }
    }
}