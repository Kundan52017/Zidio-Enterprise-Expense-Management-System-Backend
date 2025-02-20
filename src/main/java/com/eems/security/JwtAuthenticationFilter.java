package com.eems.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.extractClaims(token);
        request.setAttribute("claims", claims);
        chain.doFilter(request, response);

       /* Claims claims = jwtUtil.extractClaims(token);

        // ✅ Set authentication in the SecurityContextHolder after extracting the claims
        String username = jwtUtil.getUsername(token);
        if (username != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, null); // You can add authorities here if needed
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication); // ✅

        }

        chain.doFilter(request, response);*/
    }
    }