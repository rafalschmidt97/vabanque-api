package fi.vamk.vabanque

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class VabanqueApplication : SpringBootServletInitializer() {
  override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
    return application.sources(VabanqueApplication::class.java)
  }

  @GetMapping("/")
  fun hello(): String? {
    return "Welcome in Vabanque!"
  }
}

fun main(args: Array<String>) {
  runApplication<VabanqueApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
