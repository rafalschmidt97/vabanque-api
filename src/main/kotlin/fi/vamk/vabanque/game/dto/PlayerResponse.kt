package fi.vamk.vabanque.game.dto

import com.fasterxml.jackson.annotation.JsonProperty
import fi.vamk.vabanque.game.domain.Player

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
