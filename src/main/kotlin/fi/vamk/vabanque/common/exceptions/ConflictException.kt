package fi.vamk.vabanque.common.exceptions

import kotlin.reflect.KClass
import org.springframework.http.HttpStatus

class ConflictException(type: String, message: String) : CustomException(type, message, HttpStatus.CONFLICT) {
  companion object {
    fun alreadyExists(entity: KClass<*>, key: Any) =
      ConflictException("ALREADY_EXISTS", "${entity.simpleName!!}($key) already exists.")
  }
}
