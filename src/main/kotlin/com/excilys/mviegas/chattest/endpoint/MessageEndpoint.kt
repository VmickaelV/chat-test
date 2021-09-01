package com.excilys.mviegas.chattest.endpoint

import com.excilys.mviegas.chattest.dao.MessageDao
import com.excilys.mviegas.chattest.model.Account
import com.excilys.mviegas.chattest.model.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/api/messages")
class MessageEndpoint(private val messageDao: MessageDao) {
    //    @ApiImplicitParams(
//            ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
//                    value = "Results page you want to retrieve (0..N)"),
//            ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
//                    value = "Number of records per page."),
//            ApiImplicitParam(name = "date", dataType = "java.time.LocalDateTime", paramType = "query",
//                    value = "date since (format : JJ/MM/AA HH:SS)")
//    )
//    @ApiOperation(notes = "Automatiquement classé dans l'ordre chronologique inverse", value = "Get Messages")
    @GetMapping
    fun getMessages(/*@ApiIgnore*/ @AuthenticationPrincipal account: Account, pageable: Pageable, @RequestParam("date") optionalLocalDateTime: LocalDateTime?): Page<MessageDto> {
        return messageDao.findAllByDateLessThanEqualOrderByDateDesc((optionalLocalDateTime
                ?: LocalDateTime.now()), pageable).map { MessageDto(it) }
    }

    @PostMapping
    fun postNewMessage(/*@ApiIgnore*/ @AuthenticationPrincipal account: Account, @RequestBody requestNewMessage: RequestNewMessage) {
        MessageDto(messageDao.save(Message(UUID.randomUUID(), requestNewMessage.message, LocalDateTime.now(), account)))
    }
}

class MessageDto(val uuid: UUID, val message: String, val date: LocalDateTime, val username: String) {
    constructor(message: Message) : this(message.uuid, message.message, message.date, message.author.username)
}

class RequestNewMessage(val message: String)