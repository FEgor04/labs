package com.efedorov.lab4.backend.application.service.predicates

import com.efedorov.lab4.backend.application.service.hits.CheckHitServiceBean

interface IsHitPredicate {
    fun test(t: CheckHitServiceBean.Point): Boolean
}