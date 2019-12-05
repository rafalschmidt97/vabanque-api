package fi.vamk.vabanque.core.exceptions

import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.common.exceptions.InternalServerErrorException
import fi.vamk.vabanque.common.utilities.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class CustomExceptionHandler {
  private val logger by logger()

  @Value("\${spring.servlet.multipart.max-file-size}")
  private lateinit var maxFileSize: String

  @ExceptionHandler(value = [(CustomException::class)])
  fun handleCustomException(exception: CustomException): ResponseEntity<ExceptionResponse> {
    if (exception is InternalServerErrorException) {
      logger.warning(exception.status.reasonPhrase)
      exception.printStackTrace()
    }

    return ResponseEntity(
      ExceptionResponse(
        exception.type,
        exception.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
      ),
      exception.status
    )
  }

  @ExceptionHandler(value = [(MaxUploadSizeExceededException::class)])
  fun handleMaxUploadException(): ResponseEntity<ExceptionResponse> {
    return ResponseEntity(
      ExceptionResponse(
        HttpStatus.NOT_ACCEPTABLE.name,
        "File size exceeded. Maximum is $maxFileSize."
      ),
      HttpStatus.NOT_ACCEPTABLE
    )
  }

  @ExceptionHandler(value = [(Exception::class)])
  fun handleUnknownException(exception: Exception): ResponseEntity<ExceptionResponse> {
    logger.warning(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    exception.printStackTrace()

    return ResponseEntity(
      ExceptionResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.name,
        HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
      ),
      HttpStatus.INTERNAL_SERVER_ERROR
    )
  }
}
