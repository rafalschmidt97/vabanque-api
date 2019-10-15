package fi.vamk.vabanque.core.uploads

import fi.vamk.vabanque.common.exceptions.BadRequestException
import fi.vamk.vabanque.core.auth.security.SecurityController
import io.swagger.annotations.Api
import java.net.URL
import javax.servlet.http.HttpServletRequest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("uploads")
@Api(tags = ["Uploads"])
class UploadsController(
  private val uploadsService: UploadsService
) : SecurityController() {

  @GetMapping("{fileName}")
  fun find(@PathVariable fileName: String): ResponseEntity<Resource> {
    val file = uploadsService.find(fileName)

    val headers = HttpHeaders()
    headers.contentType = MediaType.IMAGE_JPEG

    return ResponseEntity(
      file,
      headers,
      HttpStatus.OK
    )
  }

  @PostMapping
  fun store(file: MultipartFile?, request: HttpServletRequest): URL {
    val name = uploadsService.store(file ?: throw BadRequestException("File cannot be empty."))
    val url = this.getBaseUrl(request) + "/uploads/" + name

    return URL(url)
  }

  private fun getBaseUrl(request: HttpServletRequest): String {
    return request.scheme + "://" + request.serverName + ":" + request.serverPort + request.contextPath
  }
}
