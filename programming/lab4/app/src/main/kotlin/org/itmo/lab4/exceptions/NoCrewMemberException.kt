package org.itmo.lab4.exceptions

class NoCrewMemberException(val i: Int): IndexOutOfBoundsException("no crew member with index $i") {
}