package com.excilys.mviegas.chattest.security

import com.excilys.mviegas.chattest.dao.AccountDao
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class CustomUserDetailService(private val accountDao: AccountDao) : UserDetailsService {
    override fun loadUserByUsername(username: String) = accountDao.findById(username).orElseThrow({ UsernameNotFoundException(username + " not fund") })
}