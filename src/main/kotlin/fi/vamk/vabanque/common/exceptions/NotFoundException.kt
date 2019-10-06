package fi.vamk.vabanque.common.exceptions

import kotlin.reflect.KClass
import org.springframework.http.HttpStatus

class NotFoundException(message: String) : CustomException(message, HttpStatus.NOT_FOUND) {
  constructor(entity: KClass<*>, key: Any) : this("${entity.simpleName!!}($key) not found.")
}
