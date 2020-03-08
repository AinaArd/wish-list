package ru.itis.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.security.auth.JwtAuthentication;
import ru.itis.security.details.UserDetailsImpl;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsImpl service;

    @Autowired
    public JwtAuthenticationProvider(UserDetailsImpl service) {
        this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication
                = (JwtAuthentication) authentication;
        UserDetailsImpl userDetails = (UserDetailsImpl) service.loadUserByUsername(jwtAuthentication.getLogin());
        userDetails.setToken(jwtAuthentication.getToken());
        if (userDetails != null) {
            jwtAuthentication.setUserDetails(userDetails);
            jwtAuthentication.setAuthenticated(true);
        } else {
            throw new BadCredentialsException("Incorrect Token");
        }
        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}