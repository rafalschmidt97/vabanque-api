package fi.vamk.vabanque.game

enum class GameRequestAction(val type: String) {
  CREATE("create"),
  JOIN("join"),
  LEAVE("leave"),
  SYNC("sync"),
  REMOVE("remove"),
  START("start"),
  PAUSE("pause"),
  RESUME("resume"),
  RAISE("raise"),
  RANK("rank"),
  FINISH("finish"),
}

enum class GameResponseAction(val type: String) {
  CREATED_CONFIRM("created_confirm"),
  PLAYER_JOINED("player_joined"),
  JOINED_CONFIRM("joined_confirm"),
  PLAYER_LEFT("player_left"),
  LEFT_CONFIRM("left_confirm"),
  PLAYER_DISCONNECTED("player_disconnected"),
  PLAYER_RECONNECTED("player_reconnected"),
  REMOVED("removed"),
  REMOVED_CONFIRM("removed_confirm"),
  SYNC("sync"),
  STARTED("started"),
  PAUSED("paused"),
  RESUMED("resumed"),
  RAISED("raised"),
  RANKED_CONFIRM("ranked_confirm"),
  RANKED_WAIT("ranked_wait"),
  FINISHED("finished"),
}
