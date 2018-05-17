package com.excilys.mviegas.chattest.dao

import com.excilys.mviegas.chattest.model.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import java.time.LocalDateTime
import java.util.*

interface MessageDao : PagingAndSortingRepository<Message, UUID> {
    fun findAllByDateLessThanEqualOrderByDateDesc(date: LocalDateTime = LocalDateTime.now(), pageable: Pageable) : Page<Message>
}