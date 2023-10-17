package com.efedorov.lab3.checkers

import com.efedorov.lab3.model.Point
import jakarta.annotation.ManagedBean
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Named

@ManagedBean
@ApplicationScoped
@Named("combinedHitChecker")
class CombinedHitChecker : HitChecker {
    private val allCheckers: List<HitChecker> = listOf(RectangleHitChecker(), TriangleHitChecker(), CircleHitChecker())

    override fun check(point: Point) = allCheckers.any { it.check(point) }
}