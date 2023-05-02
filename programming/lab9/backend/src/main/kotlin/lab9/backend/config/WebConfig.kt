package lab9.backend.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import lab9.backend.vehicle.requests.GetVehiclesRequestsSorting
import lab9.common.dto.VehicleColumn
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToFilterConverter())
        registry.addConverter(StringToSortingConverter())
    }
}

class StringToFilterConverter : Converter<String, VehicleFilter?> {
    override fun convert(from: String): VehicleFilter? {
        return try {
            Json.decodeFromString(from)
        } catch (_: Exception) {
            return null
        }
    }
}

class StringToSortingConverter : Converter<String, VehicleSorting> {
    override fun convert(source: String): VehicleSorting {
        return try {
            val request = Json.decodeFromString<GetVehiclesRequestsSorting>(source)
            VehicleSorting(
                VehicleColumn.values()[request.column],
                request.asc
            )
        } catch (e: Exception) {
            VehicleSorting(
                VehicleColumn.ID,
                true,
            )
        }
    }

}