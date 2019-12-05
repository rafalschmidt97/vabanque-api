package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnsupportedMediaTypeException(type: String, message: String) : CustomException(type, message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
