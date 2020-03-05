package ru.itis.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.itis.security.details.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private UserDetailsImpl userDetails;
    private String token;
    private boolean isAuthenticated;

    @Value("jwt.secret")
    private String secretKey;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userDetails != null) {
            return userDetails.getAuthorities();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails != null) {
            return userDetails;
        } else {
            return new Object();
        }
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    public void setUserDetails(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return (String) body.get("login");
    }

    public Long getId() {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return (Long) body.get("id");
    }

    public String getToken() {
        return token;
    }
}
