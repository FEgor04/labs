package lab9.backend.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import lab9.common.requests.VehicleFilter
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer{

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToFilterConverter())
    }
}

class StringToFilterConverter: Converter<String, VehicleFilter> {
    override fun convert(from: String): VehicleFilter {
        return Json.decodeFromString(from)
    }
}