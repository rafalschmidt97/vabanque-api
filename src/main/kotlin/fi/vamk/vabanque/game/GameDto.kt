package fi.vamk.vabanque.game

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime
import java.util.Date

interface GameMessagePayload {
  val gameId: String
}

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

data class PlayerResponse(
  val accountId: Long,

  @get:JsonProperty("isAdmin")
  @param:JsonProperty("isAdmin")
  val isAdmin: Boolean,

  @get:JsonProperty("isConnected")
  @param:JsonProperty("isConnected")
  val isConnected: Boolean
)

fun Player.toResponse() = PlayerResponse(accountId, isAdmin, isConnected)

data class GameTimeResponse(
  val progression: GameProgression,
  val startedAt: Date,
  val pausedAt: Date?
)

fun GameTime.toResponse() = GameTimeResponse(progression, startedAt, pausedAt)
