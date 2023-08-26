package com.chatapp.chatservice.config.security;


import com.chatapp.chatservice.config.security.client.SecurityServiceClient;
import com.chatapp.chatservice.web.dto.UserDetailsTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Log4j2
@Component
public class SecurityManager {

    private final SecurityServiceClient securityServerClient;

    // this is the jwt which contains the user details and the roles
    public Optional<Authentication> authenticate(final String authToken) {
        return getAuthenticationInfo(authToken).map(this::getAuthentication);
    }

    private Optional<UserDetailsTransfer> getAuthenticationInfo(String authToken) {
        try {
            return Optional.of(securityServerClient.checkToken(authToken));
        } catch (Exception exception) {
            log.error("Error while getting user details from security service {}", exception.getMessage());
            return Optional.empty();
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(UserDetailsTransfer userDetailsTransfer) {
        Collection<? extends GrantedAuthority> authorities = ofNullable(userDetailsTransfer.getAuthorities())
                .map(auth ->
                        auth.stream()
                                .map(perm -> (GrantedAuthority) new SimpleGrantedAuthority(perm))
                                .collect(Collectors.toSet())
                )
                .orElseGet(Set::of);
        return new UsernamePasswordAuthenticationToken(userDetailsTransfer, null, authorities);
    }
}
