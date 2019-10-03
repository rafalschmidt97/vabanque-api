package fi.vamk.vabanque.common.utilities

import java.util.*

fun getDate(field: Int, amount: Int): Date {
  val calendar = Calendar.getInstance()
  calendar.add(Calendar.YEAR, 2)
  return calendar.time
}
