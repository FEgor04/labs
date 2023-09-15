package efedorov.formatter

interface Formatter<T> {
    fun format(element: T): String
}