package fi.vamk.vabanque.accounts

import javax.persistence.*

@Entity
data class Account(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long,

  @Column(nullable = false, length = 50, unique = true)
  var email: String,

  @Column(nullable = false, length = 50)
  var password: String,

  @Column(length = 50, unique = true)
  var nickname: String? = null,

  @Column(length = 50)
  var phoneNumber: String? = null,

  @Column(length = 2048)
  var avatar: String? = null
) {
  fun update(nickname: String, phoneNumber: String, avatar: String) {
    this.nickname = nickname
    this.phoneNumber = phoneNumber
    this.avatar = avatar
  }

  override fun toString(): String {
    return "Account(id=$id, email='$email', nickname=$nickname, phoneNumber=$phoneNumber, avatar=$avatar)"
  }
}
