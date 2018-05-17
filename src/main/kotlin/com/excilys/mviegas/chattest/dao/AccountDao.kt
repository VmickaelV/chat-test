package com.excilys.mviegas.chattest.dao

import com.excilys.mviegas.chattest.model.Account
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.security.core.userdetails.UserDetailsService

interface AccountDao : PagingAndSortingRepository<Account, String>