package com.backend.teamant.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.backend.teamant.security.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 인증 작업을 위한 filter
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter 한 번의 요청에 한 번만 실행하는 filter
    // http 요청마다 token을 검사해야하기 때문에 filter를 사용함.
    public static final String HEADER_STRING = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if(StringUtils.hasText(token) && jwtTokenProvider.validateAccessToken(token)) {
            if(!request.getRequestURI().equals("/api/reissue-token")) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token, "access");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return  bearerToken.substring(7);
        }
        return  null;
    }
}
