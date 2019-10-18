package fi.vamk.vabanque.game
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

interface GameMessagePayload {
  val gameId: String
}

data class GameResponse(
  override val gameId: String,
  val code: String,
  val duration: LocalTime,
  val entry: String,
  val progression: List<ChipProgression>,
  val players: List<PlayerResponse>
) : GameMessagePayload

fun Game.toResponse() = GameResponse(id, code, duration, entry, progression, players.map { it.toResponse() })

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

data class CreateGameRequest(
  val duration: LocalTime,
  val entry: String,
  val progression: List<ChipProgression>
)

data class JoinGameRequest(
  val code: String
)

data class LeaveGameRequest(
  override val gameId: String
) : GameMessagePayload

data class LeaveGameConfirmResponse(
  override val gameId: String
) : GameMessagePayload

data class SyncGameRequest(
  override val gameId: String
) : GameMessagePayload

data class RemoveGameRequest(
  override val gameId: String
) : GameMessagePayload

data class RemoveGameResponse(
  override val gameId: String
) : GameMessagePayload

data class RemoveGameConfirmResponse(
  override val gameId: String
) : GameMessagePayload
