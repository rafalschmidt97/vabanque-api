package fi.vamk.vabanque.common.exceptions

import kotlin.reflect.KClass
import org.springframework.http.HttpStatus

class ConflictException(message: String) : CustomException(message, HttpStatus.CONFLICT) {
  companion object {
    fun alreadyExists(entity: KClass<*>, key: Any) = ConflictException("${entity.simpleName!!}($key) already exists.")
  }
}
