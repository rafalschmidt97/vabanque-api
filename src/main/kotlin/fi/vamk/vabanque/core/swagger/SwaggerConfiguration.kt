package fi.vamk.vabanque.core.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .apiInfo(getApiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("fi.vamk.vabanque"))
      .paths(PathSelectors.any())
      .build()
  }

  private fun getApiInfo(): ApiInfo {
    return ApiInfoBuilder()
      .title("VaBanque")
      .description("Modern tool for casual poker players.")
      .contact(Contact("Rafał Schmidt", "https://rafalschmidt.com", "rafalschmidt97@gmail.com"))
      .version("1.0.0")
      .build()
  }

  companion object {
    val ignoredPathsInAuth = arrayOf(
      "/v2/api-docs",
      "/configuration/ui",
      "/swagger-resources/**",
      "/configuration/security",
      "/swagger-ui.html**",
      "/webjars/**"
    )
  }
}
