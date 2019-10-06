package fi.vamk.vabanque.common.utilities

import java.util.Calendar
import java.util.Date

fun getDate(field: Int, amount: Int): Date {
  val calendar = Calendar.getInstance()
  calendar.add(field, amount)
  return calendar.time
}
