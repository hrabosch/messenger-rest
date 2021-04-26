package org.hrabosch.messenger.security;

import io.jsonwebtoken.JwtException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtTokenFilter implements Filter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        String token = jwtTokenProvider.resolveToken(req);
        if (token != null) {
            if (!jwtTokenProvider.isTokenPresentInDB(token)) {
                rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token!");
                throw new RuntimeException("Invalid JWT token");
            }
            try {
                jwtTokenProvider.validateToken(token);
            } catch (JwtException | IllegalArgumentException e) {
                rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token!");
                throw new RuntimeException("Invalid JWT token");
            }

            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
