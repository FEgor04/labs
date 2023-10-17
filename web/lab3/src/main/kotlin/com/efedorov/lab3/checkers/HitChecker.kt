package com.efedorov.lab3.checkers

import com.efedorov.lab3.model.Point

interface HitChecker {
    fun check(point: Point): Boolean
}