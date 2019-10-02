package fi.vamk.vabanque.common.data

import org.springframework.data.domain.PageRequest

fun showPage(page: Int) = PageRequest.of(page - 1, 10)
