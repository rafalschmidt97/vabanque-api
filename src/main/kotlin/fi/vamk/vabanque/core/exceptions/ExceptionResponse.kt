package fi.vamk.vabanque.core.exceptions

data class ExceptionResponse(
  val type: String,
  val message: String
)

data class HttpExceptionResponse(
  val type: String,
  val message: String,
  val code: Int
)
