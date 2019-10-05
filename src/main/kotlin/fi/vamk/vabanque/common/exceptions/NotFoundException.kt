package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

class NotFoundException(message: String) : CustomException(message, HttpStatus.NOT_FOUND) {
  constructor(entity: KClass<*>, key: Any) : this("${entity.simpleName!!}($key) not found.")
}
