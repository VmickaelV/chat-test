package com.excilys.mviegas.chattest.endpoints

import com.excilys.mviegas.chattest.dao.AccountDao
import com.excilys.mviegas.chattest.model.Account
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.websocket.server.PathParam

@RestController
class AccountEndpoint(private val authenticationManager: AuthenticationManager, private val accountDao: AccountDao, private val passwordEncoder: PasswordEncoder) {
    @GetMapping("/login/{username}/{password}")
    fun loginUgly(@PathVariable username: String?, @PathVariable password: String?): ResponseEntity<String> {
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("username and password need to be non empty")
        }

        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            ResponseEntity.ok("OK")
        } catch (e: DisabledException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: BadCredentialsException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody authenticationRequest: AuthenticationRequest) = loginUgly(authenticationRequest.username, authenticationRequest.password)

    @GetMapping("/signin/{username}/{password}")
    fun signinUgly(@PathVariable username: String, @PathVariable password: String): ResponseEntity<String> {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)

        accountDao.save(Account(username, passwordEncoder.encode(password)))

        return ResponseEntity.ok("OK")
    }

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest) = signinUgly(signinRequest.username, signinRequest.password)
}

data class AuthenticationRequest(val username: String, val password: String)

data class SigninRequest(val username: String, val password: String)