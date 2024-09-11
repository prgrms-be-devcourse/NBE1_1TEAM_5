package com.example.gccoffee.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            // 요청에서 JWT 토큰을 추출
            String jwt = getJwtFromRequest(request);

            // 토큰이 유효한지 확인
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            }
        } catch (Exception ex) {
            // 예외 처리
            ex.printStackTrace();
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // 요청의 헤더에서 JWT 토큰을 추출
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 이후의 실제 토큰 부분 추출
        }
        return null;
    }
}
