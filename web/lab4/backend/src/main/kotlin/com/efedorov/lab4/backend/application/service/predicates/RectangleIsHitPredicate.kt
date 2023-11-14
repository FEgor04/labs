package com.efedorov.lab4.backend.application.service.predicates

import com.efedorov.lab4.backend.application.service.hits.CheckHitServiceBean
import jakarta.ejb.Stateless

@Stateless
class RectangleIsHitPredicate: IsHitPredicate {
    override fun test(t: CheckHitServiceBean.Point): Boolean {
        val (x, y, r) = t
        return (x in -r/2..0.0) && (y in 0.0..r)
    }
}