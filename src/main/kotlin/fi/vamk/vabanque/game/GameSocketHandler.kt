package fi.vamk.vabanque.game

import fi.vamk.vabanque.common.exceptions.BadRequestException
import fi.vamk.vabanque.core.socket.SocketHandler
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.game.actions.createGame
import fi.vamk.vabanque.game.actions.joinGame
import fi.vamk.vabanque.game.actions.leaveGame
import fi.vamk.vabanque.game.actions.markInGamesAsDisconnected
import fi.vamk.vabanque.game.actions.pauseGame
import fi.vamk.vabanque.game.actions.raiseGame
import fi.vamk.vabanque.game.actions.removeGame
import fi.vamk.vabanque.game.actions.resumeGame
import fi.vamk.vabanque.game.actions.startGame
import fi.vamk.vabanque.game.actions.syncGame
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession

class GameSocketHandler : SocketHandler() {
  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    super.afterConnectionClosed(session, status)
    markInGamesAsDisconnected(session)
  }

  override fun handleMessage(session: WebSocketSession, message: SocketMessage) {
    when (message.type) {
      GameRequestAction.CREATE.type -> {
        createGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.JOIN.type -> {
        joinGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.LEAVE.type -> {
        leaveGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.SYNC.type -> {
        syncGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.REMOVE.type -> {
        removeGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.START.type -> {
        startGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.PAUSE.type -> {
        pauseGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.RESUME.type -> {
        resumeGame(session, payloadToObject(message.payload))
      }
      GameRequestAction.RAISE.type -> {
        raiseGame(session, payloadToObject(message.payload))
      }
      else -> {
        throw BadRequestException("Action type not found.")
      }
    }
  }
}
