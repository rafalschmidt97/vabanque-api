package fi.vamk.vabanque.core.uploads

import fi.vamk.vabanque.common.exceptions.InternalServerErrorException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.common.utilities.getRandomAlfaNumeric
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadsService {
  private val location: Path = Paths.get("./uploads").toAbsolutePath().normalize()

  init {
    try {
      Files.createDirectories(location)
    } catch (e: IOException) {
      throw throw InternalServerErrorException("Could not create directory for uploads.", e)
    }
  }

  fun find(fileName: String): Resource {
    val path = location.resolve(fileName).normalize()
    val resource = UrlResource(path.toUri())

    if (!resource.exists()) {
      throw NotFoundException("File does not exists.")
    }

    return resource
  }

  fun store(file: MultipartFile): String {
    val name = getRandomAlfaNumeric(30) + getFileExtension(file).toLowerCase()

    try {
      val targetLocation = location.resolve(name)
      Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
      return name
    } catch (e: IOException) {
      throw throw InternalServerErrorException("Problem occurred while copying file to target location.", e)
    }
  }

  private fun getFileExtension(file: MultipartFile): String {
    return file.originalFilename!!.substring(file.originalFilename!!.lastIndexOf('.'))
  }
}
