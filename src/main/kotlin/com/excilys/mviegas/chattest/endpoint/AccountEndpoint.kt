package com.excilys.mviegas.chattest.endpoint

import com.excilys.mviegas.chattest.dao.AccountDao
import com.excilys.mviegas.chattest.model.Account
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountEndpoint(private val authenticationManager: AuthenticationManager, private val accountDao: AccountDao, private val passwordEncoder: PasswordEncoder) {
    @PostMapping("/login")
    fun login(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<String> {
        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password))
            ResponseEntity.ok("OK")
        } catch (e: DisabledException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: BadCredentialsException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest): ResponseEntity<String> {
        accountDao.save(Account(signinRequest.username, passwordEncoder.encode(signinRequest.password)))

        return ResponseEntity.ok("OK")
    }
}

data class AuthenticationRequest(val username: String, val password: String)

data class SigninRequest(val username: String, val password: String)