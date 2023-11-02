package com.example.dip_neto.jwtoken;

import com.example.dip_neto.exeptions.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
@Component
public class TokenFilter extends GenericFilterBean{
        private final TokenCreator tokenCreator;

        public TokenFilter(TokenCreator tokenCreator) {
            this.tokenCreator = tokenCreator;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {

            String token = tokenCreator.resolveToken((HttpServletRequest) servletRequest);

            try {
                if (token != null && tokenCreator.validateToken(token)) {
                    Authentication authentication = tokenCreator.getAuthentication(token);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (AuthException e) {
                SecurityContextHolder.clearContext();
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (AccessDeniedException ex) {
                SecurityContextHolder.clearContext();
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
}
