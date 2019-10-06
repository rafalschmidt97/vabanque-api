package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnsupportedMediaTypeException(message: String) : CustomException(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
