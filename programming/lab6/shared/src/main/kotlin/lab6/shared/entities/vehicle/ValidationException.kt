package lab6.shared.entities.vehicle

class ValidationException(fieldName: String, hint: String) : Exception("Invalid field $fieldName: $hint")