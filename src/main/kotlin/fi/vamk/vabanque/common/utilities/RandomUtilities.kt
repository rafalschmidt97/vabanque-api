package fi.vamk.vabanque.common.utilities

import java.util.Base64

fun getRandomAlfaNumeric(length: Int): String {
  val alphaNumeric = ('a'..'z') + ('A'..'Z') + ('0'..'9')
  return alphaNumeric.shuffled().take(length).joinToString("")
}

fun getRandomBase64(): String {
  val randomText = getRandomAlfaNumeric(32)
  return Base64.getEncoder().encodeToString(randomText.toByteArray())
}
