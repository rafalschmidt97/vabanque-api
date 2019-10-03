package fi.vamk.vabanque.common.exceptions

abstract class CustomException(message: String, val status: Int) : RuntimeException(message)
