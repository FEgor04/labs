package org.itmo.lab4.look.context

import org.itmo.lab4.look.lookable.Lookable
import org.itmo.lab4.look.looker.Illuminator
import org.itmo.lab4.look.looker.Looker
import org.itmo.lab4.look.looker.Telescope
import org.itmo.lab4.look.quality.LookQuality
import org.itmo.lab4.planets.Moon

class LookContext(private val looker: Looker, private val lookable: Lookable) {
    private val quality: LookQuality
    init {
        quality = this.calculateLookQuality()
    }

    fun getSeenObjects(): Any {
        return looker.getSeenObjects(lookable)
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

    override fun hashCode(): Int {
        return this.looker.hashCode() + 10 * this.quality.hashCode() + 100 * this.lookable.hashCode();
    }

    override fun toString(): String {
        return "${this.looker} -> ${this.lookable}: ${this.quality}"
    }

    override fun equals(other: Any?): Boolean {
        if(other is LookContext) {
            return this.looker == other.looker && this.lookable == other.lookable && this.quality == other.quality
        }
        return false
    }

    companion object {
        fun New(looker: Looker, lookable: Lookable): LookContext {
            val context = LookContext(looker, lookable)
            context.calculateLookQuality()
            return context
        }
    }
}