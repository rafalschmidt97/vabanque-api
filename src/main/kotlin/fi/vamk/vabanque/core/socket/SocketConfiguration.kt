package fi.vamk.vabanque.core.socket

import fi.vamk.vabanque.game.GameSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class SocketConfiguration(private val socketHandler: GameSocketHandler) : WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry
      .addHandler(socketHandler, "/game")
      .setAllowedOrigins("*")
      .withSockJS()
  }
}
