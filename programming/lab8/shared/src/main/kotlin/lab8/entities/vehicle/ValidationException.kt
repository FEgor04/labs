package lab8.entities.vehicle

class ValidationException(fieldName: String, hint: String) : Exception("Invalid field $fieldName: $hint")
