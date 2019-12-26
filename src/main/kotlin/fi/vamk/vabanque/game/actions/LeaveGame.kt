package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.domain.Game
import fi.vamk.vabanque.game.domain.GameStatus
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.dto.PlayerResponse
import fi.vamk.vabanque.game.dto.toResponse
import fi.vamk.vabanque.game.findPlayerNonAdminNonSelf
import fi.vamk.vabanque.game.publishGame
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class LeaveGameRequest(
  override val gameId: String
) : GameMessagePayload

data class LeftGameResponse(
  override val gameId: String,
  val player: PlayerResponse
) : GameMessagePayload

data class LeftGameConfirmResponse(
  override val gameId: String
) : GameMessagePayload

fun leaveGame(session: WebSocketSession, request: LeaveGameRequest) {
  try {
    val (game, player) = gameAction(session, request)

    if (game.status == GameStatus.FINISHED) {
      throw ConflictException("GAME_FINISHED", "${Game::class.simpleName!!}(${game.id}) is finished. Wait for the ranking instead.")
    }

    if (game.players.size > 1) {
      if (player.isAdmin) {
        val nonAdminOtherPlayer = findPlayerNonAdminNonSelf(game, player.accountId)!!
        nonAdminOtherPlayer.makeAdmin()

        if (game.status == GameStatus.IN_LOBBY) {
          game.players.remove(player)
        } else {
          player.disconnect()
        }

        publishGame(SocketMessage(GameResponseAction.SYNC.type, game.toResponse()), game)
      } else {
        game.players.remove(player)
        publishGame(
          SocketMessage(
            GameResponseAction.PLAYER_LEFT.type,
            LeftGameResponse(game.id, player.toResponse())
          ), game
        )
      }
    } else {
      GameState.games.remove(game.id)
    }

    session.publish(
      SocketMessage(
        GameResponseAction.LEFT_CONFIRM.type,
        LeftGameConfirmResponse(game.id)
      )
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_LEAVE_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
