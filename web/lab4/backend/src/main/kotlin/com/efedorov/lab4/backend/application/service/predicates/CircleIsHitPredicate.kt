package com.efedorov.lab4.backend.application.service.predicates

import com.efedorov.lab4.backend.application.service.hits.CheckHitServiceBean
import jakarta.ejb.Stateless
import kotlin.math.pow

@Stateless
class CircleIsHitPredicate: IsHitPredicate {
    override fun test(t: CheckHitServiceBean.Point): Boolean {
        val (x, y, r) = t
        return (x <= 0 && y <= 0) && ((x * x + y * y) <= (r/2).pow(2))
    }
}