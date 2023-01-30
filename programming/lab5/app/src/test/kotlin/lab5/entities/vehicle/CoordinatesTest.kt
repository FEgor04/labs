package lab5.entities.vehicle

import lab5.entities.ValidationException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows

class CoordinatesTest {

    @Test
    fun `Should be OK`() {
        val coordinates = Coordinates(123, 123)
        coordinates.validate()
    }

    @Test
    fun `Bad X coordinate`() {
        val coordinates = Coordinates(-600, 123)
        assertThrows<ValidationException> {
            coordinates.validate()
        }
    }
}