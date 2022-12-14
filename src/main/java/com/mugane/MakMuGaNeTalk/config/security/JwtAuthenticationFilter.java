package com.mugane.MakMuGaNeTalk.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugane.MakMuGaNeTalk.exception.ErrorCode;
import com.mugane.MakMuGaNeTalk.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {

        try {
            String path = request.getServletPath();
            if (path.startsWith("/api/v1/users/signup")
                || path.startsWith("/api/v1/users/signin")
                || path.startsWith("/api/v1/users/reissue")
                || path.startsWith("/api/v1/users/oauth")
                || path.startsWith("/api/v1/users/email-check")
                || path.startsWith("/api/v1/users/nickname-check")
                || path.startsWith("/ws")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || !path.contains("/api")
            ) {
                chain.doFilter(request, response);
            } else {
                String token = jwtTokenProvider.resolveToken(request);
                boolean isTokenValid = jwtTokenProvider.validateToken(token);

                if (token != null && isTokenValid) {
                    setAuthentication(token);
                }

                chain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            ErrorResponse errorResponse = ErrorResponse
                .builder()
                .errorCode(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED)
                .build();

            response.setContentType("application/json");
            response.setStatus(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getStatus().value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
