package lab8.properties

import java.util.*

@Suppress("SwallowedException")
fun Properties.getInt(key: String, defaultValue: Int): Int {
    try {
        return this.getProperty(key, "$defaultValue").toInt()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Field $key should be int, but it is not")
    }
}

@Suppress("SwallowedException")
fun Properties.getIntOrNull(key: String): Int? {
    try {
        return this.getProperty(key).toIntOrNull()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Field $key should be int, but it is not")
    }
}

@Suppress("SwallowedException")
fun Properties.getByte(key: String, defaultValue: Byte): Byte {
    try {
        return this.getProperty(key, "$defaultValue").toByte()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Field $key should be a byte, but it is not")
    }
}

@Suppress("SwallowedException")
fun Properties.getByteOrNull(key: String): Byte? {
    try {
        return this.getProperty(key).toByteOrNull()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Field $key should be a byte, but it is not")
    }
}

@Suppress("SwallowedException")
fun Properties.getLong(key: String, defaultValue: Long): Long {
    try {
        return this.getProperty(key, "$defaultValue").toLong()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Field $key should be a long, but it is not")
    }
}
