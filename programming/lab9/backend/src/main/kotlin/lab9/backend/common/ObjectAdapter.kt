package lab9.backend.common

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component


@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ObjectAdapter(
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @get:AliasFor(annotation = Component::class) val value: String = ""
)