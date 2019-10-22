package fi.vamk.vabanque.game.domain

import fi.vamk.vabanque.common.exceptions.ConflictException
import java.util.Date

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
