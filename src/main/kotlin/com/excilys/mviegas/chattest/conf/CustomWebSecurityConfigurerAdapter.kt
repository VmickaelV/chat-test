package com.excilys.mviegas.chattest.conf

import com.excilys.mviegas.chattest.security.CustomUserDetailService
import com.excilys.mviegas.chattest.security.MyBasicAuthenticationEntryPoint
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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = [CustomUserDetailService::class])
class CustomWebSecurityConfigurerAdapter(
        private val customUserDetailService: CustomUserDetailService,
        private val authenticationEntryPoint: MyBasicAuthenticationEntryPoint,
        private val passwordEncoder: PasswordEncoder,
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder)
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedMethods("*")
            }
        }
    }


    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/login", "/signin").permitAll()
                .antMatchers("/signin/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)

        http.csrf().disable().cors()
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
                .antMatchers("/swagger-ui**", "/v3/api-docs/**")
                .antMatchers("/h2-console/**/**")
                .antMatchers("/signin", "/login")
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}