package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.domain.Player
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.dto.PlayerResponse
import fi.vamk.vabanque.game.dto.toResponse
import fi.vamk.vabanque.game.findGameByCodeOrThrow
import fi.vamk.vabanque.game.findPlayerInGameBySession
import fi.vamk.vabanque.game.publishGameExcludeSelf
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class JoinGameRequest(
  val code: String
)

data class JoinedGameResponse(
  override val gameId: String,
  val player: PlayerResponse
) : GameMessagePayload

fun joinGame(session: WebSocketSession, request: JoinGameRequest) {
  try {
    val game = findGameByCodeOrThrow(request.code)
    var (player, accountId) = findPlayerInGameBySession(session, game)

    if (player == null) {
      player = Player(accountId)
      game.players.add(player)

      publishGameExcludeSelf(
        SocketMessage(
          GameResponseAction.JOINED.type,
          JoinedGameResponse(game.id, player.toResponse())
        ),
        game,
        session
      )
    } else {
      player.reconnect()

      publishGameExcludeSelf(
        SocketMessage(
          GameResponseAction.RECONNECTED.type,
          JoinedGameResponse(game.id, player.toResponse())
        ),
        game,
        session
      )
    }

    session.publish(
      SocketMessage(
        GameResponseAction.JOINED_CONFIRM.type,
        game.toResponse()
      )
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_JOIN_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
