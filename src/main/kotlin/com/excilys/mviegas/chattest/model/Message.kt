package com.excilys.mviegas.chattest.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Message(@Id val uuid: UUID = UUID.randomUUID(), val message: String = "", val date:LocalDateTime = LocalDateTime.now(), @ManyToOne val author:Account = Account())