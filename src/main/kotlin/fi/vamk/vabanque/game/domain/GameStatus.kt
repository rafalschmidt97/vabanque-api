package fi.vamk.vabanque.game.domain

enum class GameStatus(val type: String) {
  IN_LOBBY("in_lobby"),
  PLAYING("playing"),
  PAUSED("paused"),
  FINISHED("finished"),
}
