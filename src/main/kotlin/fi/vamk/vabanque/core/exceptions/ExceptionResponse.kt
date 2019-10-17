package fi.vamk.vabanque.core.exceptions

data class ExceptionResponse(
  val message: String
)

data class HttpExceptionResponse(
  val message: String,
  val code: Int
)
