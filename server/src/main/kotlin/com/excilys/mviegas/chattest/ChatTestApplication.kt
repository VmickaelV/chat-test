package com.excilys.mviegas.chattest

import com.excilys.mviegas.chattest.conf.SwaggerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(SwaggerConfiguration::class)
class ChatTestApplication

fun main(args: Array<String>) {
    runApplication<ChatTestApplication>(*args)
}
