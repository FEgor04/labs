package lab6.shared.entities.vehicle

class FactoryException(fieldName: String, desc: String) : Exception("Bad $fieldName value: $desc")