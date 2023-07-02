package com.chatapp.chatservice.config.security.filter;


import com.chatapp.chatservice.config.security.SecurityManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityManager securityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && !token.isBlank()) {
            // gets the jwt token from the header
            String tokenData = StringUtils.substringAfter(token, "bearer ");
            securityManager.authenticate(tokenData).ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }
        filterChain.doFilter(request, response);


    }

}
