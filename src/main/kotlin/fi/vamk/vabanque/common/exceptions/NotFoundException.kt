package fi.vamk.vabanque.common.exceptions

import kotlin.reflect.KClass
import org.springframework.http.HttpStatus

class NotFoundException(type: String, message: String) : CustomException(type, message, HttpStatus.NOT_FOUND) {
  constructor(message: String) : this(HttpStatus.NOT_FOUND.name, message)
  constructor(entity: KClass<*>, key: Any) : this("NOT_FOUND", "${entity.simpleName!!}($key) not found.")
}
