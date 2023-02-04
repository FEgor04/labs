package lab5.cli.utils

class BadEnumValueException(val possibleValues: List<String>): Exception("No element of enum. Possible values:$possibleValues") {
}