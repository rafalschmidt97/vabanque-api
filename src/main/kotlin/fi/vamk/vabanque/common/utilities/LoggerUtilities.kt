package fi.vamk.vabanque.common.utilities

import java.util.logging.Logger
import kotlin.reflect.full.companionObject

fun <R : Any> R.logger(): Lazy<Logger> {
  return lazy { Logger.getLogger(unwrapCompanionClass(this.javaClass).name) }
}

private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
  return ofClass.enclosingClass?.takeIf {
    ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
  } ?: ofClass
}
