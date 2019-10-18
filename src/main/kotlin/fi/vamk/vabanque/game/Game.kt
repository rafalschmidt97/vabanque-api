package fi.vamk.vabanque.game

import fi.vamk.vabanque.common.utilities.getRandomAlfaNumeric
import java.time.LocalTime
import java.util.UUID

object GameState {
  val games = HashMap<String, Game>()
}

data class Game(
  val id: String,
  val code: String,
  val duration: LocalTime,
  val entry: String,
  val progression: List<ChipProgression>,
  val players: MutableList<Player>
) {
  constructor(duration: LocalTime, entry: String, progression: List<ChipProgression>, adminId: Long) :
    this(UUID.randomUUID().toString(), getRandomAlfaNumeric(8), duration, entry, progression, mutableListOf(Player(adminId, true)))

  override fun toString(): String {
    return "Game(id=$id, duration=$duration, entry='$entry', progression=$progression)"
  }
}

data class ChipProgression(
  val small: Int,
  val big: Int
)

data class Player(
  val accountId: Long,
  var admin: Boolean = false,
  var connected: Boolean = true
) {
  fun disconnect() {
    connected = false
    admin = false
  }

  fun reconnect() {
    connected = true
  }

  fun makeAdmin() {
    admin = true
  }
}
