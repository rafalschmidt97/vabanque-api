package fi.vamk.vabanque.game.dto

import fi.vamk.vabanque.game.GameTimeResponse
import fi.vamk.vabanque.game.domain.Game
import fi.vamk.vabanque.game.domain.GameProgression
import fi.vamk.vabanque.game.toResponse
import java.time.LocalTime

data class GameResponse(
  override val gameId: String,
  val code: String,
  val duration: LocalTime,
  val entry: String,
  val progression: List<GameProgression>,
  val currentProgression: GameProgression,
  val players: List<PlayerResponse>,
  val status: String,
  val time: List<GameTimeResponse>
) : GameMessagePayload

fun Game.toResponse() = GameResponse(
  id,
  code,
  duration,
  entry,
  progression,
  currentProgression,
  players.map { it.toResponse() },
  status.type,
  time.map { it.toResponse() }
)
