package fi.vamk.vabanque.game

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.utilities.getRandomAlfaNumeric
import java.time.LocalTime
import java.util.Date
import java.util.UUID

object GameState {
  val games = HashMap<String, Game>()
}

data class Game(
  val id: String,
  val code: String,
  val duration: LocalTime,
  val entry: String,
  val progression: MutableList<GameProgression>,
  val players: MutableList<Player>
) {
  var status = GameStatus.IN_LOBBY
    private set

  val time = mutableListOf<GameTime>()

  val currentProgression: GameProgression
    get() = progression[currentProgressionIndex]

  val currentTime: GameTime
    get() = time[currentTimeIndex]

  val previousTime: GameTime?
    get() = if (currentTimeIndex != 0) time[currentTimeIndex - 1] else null

  private var currentProgressionIndex = 0
  private var currentTimeIndex = 0

  constructor(duration: LocalTime, entry: String, progression: MutableList<GameProgression>, adminId: Long) :
    this(
      UUID.randomUUID().toString(),
      getRandomAlfaNumeric(8),
      duration,
      entry,
      progression,
      mutableListOf(Player(adminId, true))
    )

  fun start() {
    status = GameStatus.PLAYING
    time.add(GameTime(currentProgression))
  }

  fun pause() {
    currentTime.pause()
    status = GameStatus.PAUSED
  }

  fun resume() {
    if (!currentTime.isPaused) {
      throw ConflictException("${Game::class.simpleName!!} is not paused.")
    }

    time.add(GameTime(currentProgression))
    status = GameStatus.PLAYING
    currentTimeIndex++
  }

  fun raise() {
    currentTime.pause()
    currentTimeIndex++

    if (currentProgressionIndex + 1 == progression.size) {
      progression.add(GameProgression(currentProgression.small * 2, currentProgression.big * 2))
    }

    currentProgressionIndex++
    time.add(GameTime(currentProgression))
  }

  override fun toString(): String {
    return "Game(id=$id, duration=$duration, entry='$entry', progression=$progression)"
  }
}

enum class GameStatus(val type: String) {
  IN_LOBBY("in_lobby"),
  PLAYING("playing"),
  PAUSED("paused"),
}

data class GameTime(
  val progression: GameProgression,
  val startedAt: Date = Date(),
  var pausedAt: Date? = null
) {
  val isPaused: Boolean
    get() = pausedAt != null

  fun pause() {
    if (pausedAt != null) {
      throw ConflictException("${Game::class.simpleName!!} is already paused.")
    }

    pausedAt = Date()
  }
}

data class GameProgression(
  val small: Int,
  val big: Int
)

data class Player(
  val accountId: Long,
  var isAdmin: Boolean = false,
  var isConnected: Boolean = true
) {
  fun disconnect() {
    isConnected = false
    isAdmin = false
  }

  fun reconnect() {
    isConnected = true
  }

  fun makeAdmin() {
    isAdmin = true
  }
}
