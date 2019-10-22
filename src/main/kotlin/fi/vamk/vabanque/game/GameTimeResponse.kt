package fi.vamk.vabanque.game

import fi.vamk.vabanque.game.domain.GameProgression
import fi.vamk.vabanque.game.domain.GameTime
import java.util.Date

data class GameTimeResponse(
  val progression: GameProgression,
  val startedAt: Date,
  val pausedAt: Date?
)

fun GameTime.toResponse() = GameTimeResponse(progression, startedAt, pausedAt)
