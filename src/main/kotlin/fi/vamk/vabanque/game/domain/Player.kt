package fi.vamk.vabanque.game.domain

data class Player(
  val accountId: Long,
  var isAdmin: Boolean = false,
  var isConnected: Boolean = true
) {
  fun disconnect() {
    isConnected = false
    isAdmin = false
  }

  fun reconnect() {
    isConnected = true
  }

  fun makeAdmin() {
    isAdmin = true
  }
}
