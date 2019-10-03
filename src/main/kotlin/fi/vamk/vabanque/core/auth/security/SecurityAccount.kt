package fi.vamk.vabanque.core.auth.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityAccount(val id: Long) : UserDetails {
  override fun getAuthorities() = mutableListOf<GrantedAuthority>()
  override fun isEnabled() = true
  override fun getUsername() = ""
  override fun isCredentialsNonExpired() = true
  override fun getPassword() = ""
  override fun isAccountNonExpired() = true
  override fun isAccountNonLocked() = true
}

