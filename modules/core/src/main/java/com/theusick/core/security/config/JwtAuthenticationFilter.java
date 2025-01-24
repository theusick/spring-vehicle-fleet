package com.theusick.core.security.config;

import com.theusick.core.security.service.DBUserDetailsService;
import com.theusick.core.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DBUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        final String bearerToken = request.getHeader("Authorization");

        if (NotValidAuthorizationHeader(bearerToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = extractJwtFromHeader(bearerToken);

        if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
            String username = extractUsernameFromJwt(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (StringUtils.hasText(username) && (authentication == null)) {
                authenticateUser(request, jwt, username);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean NotValidAuthorizationHeader(String bearerToken) {
        return !StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ");
    }

    private String extractJwtFromHeader(String bearerToken) {
        return bearerToken.substring(7);
    }

    private String extractUsernameFromJwt(String jwt) {
        return jwtService.extractUsername(jwt);
    }

    private void authenticateUser(HttpServletRequest request,
                                  String jwt,
                                  String userEmail) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        if (jwtService.isTokenUserValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

}
