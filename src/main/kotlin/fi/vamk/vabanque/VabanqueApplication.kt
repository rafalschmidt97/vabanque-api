package fi.vamk.vabanque

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VabanqueApplication

fun main(args: Array<String>) {
	runApplication<VabanqueApplication>(*args)
}
