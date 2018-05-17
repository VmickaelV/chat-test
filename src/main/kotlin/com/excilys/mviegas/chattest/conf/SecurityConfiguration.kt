package com.excilys.mviegas.chattest.conf

import com.excilys.mviegas.chattest.security.CustomUserDetailService
import com.excilys.mviegas.chattest.security.MyBasicAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = arrayOf(CustomUserDetailService::class))
class CustomWebSecurityConfigurerAdapter(private val customUserDetailService: CustomUserDetailService) : WebSecurityConfigurerAdapter() {

    @Autowired
    private val authenticationEntryPoint: MyBasicAuthenticationEntryPoint? = null

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder, passwordEncoder: PasswordEncoder) {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder)
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/signin").permitAll()
                .antMatchers("/signin/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)

        http.csrf().disable()
//        http.addFilterAfter(CustomFilter(),
//                BasicAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        // AuthenticationTokenFilter will ignore the below paths
        web!!
                // allow anonymous resource requests
                .ignoring()
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif")

                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**")
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}