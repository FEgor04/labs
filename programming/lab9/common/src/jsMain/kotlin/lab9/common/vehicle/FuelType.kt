@file:OptIn(ExperimentalJsExport::class)

package lab9.common.vehicle

import kotlinx.serialization.Serializable

@Serializable
@JsExport
actual enum class FuelType {
    GASOLINE, ELECTRICITY, MANPOWER, PLASMA, ANTIMATTER
}