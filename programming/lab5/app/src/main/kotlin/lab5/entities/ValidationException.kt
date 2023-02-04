package lab5.entities

class ValidationException(fieldName: String, hint: String) : Exception("Invalid field $fieldName: $hint")