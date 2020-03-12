package ru.itis.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.security.auth.JwtAuthentication;
import ru.itis.security.providers.JwtAuthenticationProvider;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {
    private final static String AUTH_HEADER = "Authorization";
    private JwtAuthenticationProvider provider;

    @Autowired
    public JwtAuthenticationFilter(JwtAuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String headerValue = request.getHeader(AUTH_HEADER);
        JwtAuthentication authentication = new JwtAuthentication();

        if (headerValue != null) {
            authentication.setToken(headerValue);
            SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
        } else {
            authentication.setAuthenticated(false);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}