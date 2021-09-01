package com.excilys.mviegas.chattest.security

import com.excilys.mviegas.chattest.dao.AccountDao
import com.excilys.mviegas.chattest.model.Account
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(private val accountDao: AccountDao) : UserDetailsService {
    override fun loadUserByUsername(username: String): Account = accountDao.findById(username).orElseThrow { UsernameNotFoundException("$username not fund") }
}