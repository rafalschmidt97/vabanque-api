package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameMessagePayload
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.PlayerResponse
import fi.vamk.vabanque.game.findPlayerInGame
import fi.vamk.vabanque.game.findPlayerNonAdminNonSelf
import fi.vamk.vabanque.game.publishGame
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

data class DisconnectedGameResponse(
  override val gameId: String,
  val player: PlayerResponse
) : GameMessagePayload

fun markInAllAsDisconnectedGame(session: WebSocketSession) {
  val accountId = session.getAccountId()
  val disconnectingFromGames = findPlayerGames(accountId)

  disconnectingFromGames.values.forEach { disconnectingGame ->
    val disconnectingPlayer = findPlayerInGame(disconnectingGame, accountId)!!

    if (disconnectingGame.players.size > 1) {
      if (disconnectingPlayer.isAdmin) {
        val nonAdminOtherPlayer = findPlayerNonAdminNonSelf(disconnectingGame, accountId)!!
        nonAdminOtherPlayer.makeAdmin()
        disconnectingPlayer.disconnect()
        publishGame(SocketMessage(GameResponseAction.SYNC.type, disconnectingGame.toResponse()), disconnectingGame)
      } else {
        disconnectingPlayer.disconnect()
        publishGame(
          SocketMessage(
            GameResponseAction.DISCONNECTED.type,
            DisconnectedGameResponse(disconnectingGame.id, disconnectingPlayer.toResponse())
          ), disconnectingGame
        )
      }
    } else {
      GameState.games.remove(disconnectingGame.id)
    }
  }
}

private fun findPlayerGames(accountId: Long): Map<String, Game> {
  return GameState.games.filterValues { game ->
    findPlayerInGame(game, accountId) != null
  }
}
