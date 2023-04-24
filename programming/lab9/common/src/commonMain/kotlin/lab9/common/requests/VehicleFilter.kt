@file:OptIn(ExperimentalJsExport::class)

package lab9.common.requests

import kotlinx.serialization.Serializable
import lab9.common.dto.VehicleColumn
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
sealed class VehicleFilter() {
    abstract val filterColumn: VehicleColumn

    @Serializable
    sealed class NumberFilter<T> :
        VehicleFilter() {
        abstract val lowerBound: T?
        abstract val upperBound: T?
    }

    @Serializable
    sealed class IntFilter : NumberFilter<Int>()
    @Serializable
    sealed class LongFilter : NumberFilter<Long>()

    @Serializable
    data class IDFilter(
        override val lowerBound: Int?,
        override val upperBound: Int?
    ) : IntFilter() {
        override val filterColumn: VehicleColumn = VehicleColumn.ID
    }

    @Serializable
    data class XFilter(
        override val lowerBound: Int?,
        override val upperBound: Int?
    ) : IntFilter() {
        override val filterColumn: VehicleColumn = VehicleColumn.COORDINATES_X
    }}