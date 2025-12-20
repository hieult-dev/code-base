package com.auth.controller

import com.auth.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import com.utils.ResponseUtil
import io.jsonwebtoken.JwtException

@Component
class JwtAuthenticationFilterController(
    private val jwtService: JwtService,
    private val userDetailService: UserDetailsService
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI ?: ""
        return request.method.equals("OPTIONS", true) ||
                path.startsWith("/api/auth/") ||   // login, register, refresh...
                path == "/graphql"
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)

        try {
            val userEmail = jwtService.extractUserName(jwt)

            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails: UserDetails = userDetailService.loadUserByUsername(userEmail)
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    ).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
                    SecurityContextHolder.getContext().authentication = authToken
                } else {
                    ResponseUtil.writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN")
                    return
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            ResponseUtil.writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "ACCESS_TOKEN_EXPIRED")
        } catch (e: JwtException) {
            ResponseUtil.writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN")
        } catch (e: Exception) {
            ResponseUtil.writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED")
        }
    }

}