package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

class ConflictException(message: String) : CustomException(message, HttpStatus.CONFLICT.value()) {
  companion object {
    fun alreadyExists(entity: KClass<*>, key: Any) = ConflictException("${entity.simpleName!!}($key) already exists.")
  }
}
