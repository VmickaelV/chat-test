package com.excilys.mviegas.chattest.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter

@Component
class MyBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authEx: AuthenticationException?) {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\"")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val writer = response.writer
        writer.println("HTTP Status 401 - " + authEx!!.message)
    }

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        realmName = "Excilys"
        super.afterPropertiesSet()
    }
}
