package lab8.entities.vehicle

class FactoryException(fieldName: String, desc: String) : Exception("Bad $fieldName value: $desc")
