package org.itmo.lab3.look.context

import org.itmo.lab3.look.lookable.Lookable
import org.itmo.lab3.look.looker.Illuminator
import org.itmo.lab3.look.looker.Looker
import org.itmo.lab3.look.looker.Telescope
import org.itmo.lab3.look.quality.LookQuality
import org.itmo.lab3.planets.Earth
import org.itmo.lab3.planets.Moon

class LookContext(private val looker: Looker, private val lookable: Lookable) {
    private val quality: LookQuality
    init {
        quality = this.calculateLookQuality()
    }

    fun getSeenObjects(): Any {
        return looker.getSeenObjects()
    }

    fun getQuality(): LookQuality {
        return this.quality
    }

    private fun calculateLookQuality(): LookQuality {
        if(looker is Telescope) {
            if (lookable is Moon) {
                return LookQuality.EARTH_LIKE
            }
        }
        if(looker is Illuminator) {
            return LookQuality.BETTER_THAN_EARTH
        }
        return LookQuality.IMPOSSIBLE
    }

    companion object {
        fun New(looker: Looker, lookable: Lookable): LookContext {
            val context = LookContext(looker, lookable)
            context.calculateLookQuality()
            return context
        }
    }
}