package lab5.entities

class FactoryException(fieldName: String, desc: String) : Exception("Bad $fieldName value: $desc")