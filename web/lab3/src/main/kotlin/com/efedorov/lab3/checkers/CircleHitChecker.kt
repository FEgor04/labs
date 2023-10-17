package com.efedorov.lab3.checkers

import com.efedorov.lab3.model.Point

class CircleHitChecker: HitChecker {
    override fun check(point: Point): Boolean {
        val (x, y, r) = point
        if(x >= 0 && y >= 0) {
            return x * x + y * y <= (r * r) / 4;
        }
        return false;
    }
}