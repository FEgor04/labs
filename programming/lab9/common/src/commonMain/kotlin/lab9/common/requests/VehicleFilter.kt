@file:OptIn(ExperimentalJsExport::class)

package lab9.common.requests

import kotlinx.serialization.Serializable
import lab9.common.dto.VehicleColumn
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
sealed class VehicleFilter() {
    abstract  val column: VehicleColumn
    sealed class NumberFilter<T>:
        VehicleFilter() {
            abstract val lowerBound: T
            abstract val upperBound: T
        }

    data class IDFilter(
        override val lowerBound: Int,
        override val upperBound: Int
    ) : NumberFilter<Int>() {
        override val column: VehicleColumn = VehicleColumn.ID
    }
}