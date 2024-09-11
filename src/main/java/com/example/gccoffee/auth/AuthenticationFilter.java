package com.example.gccoffee.auth;

import com.example.gccoffee.auth.withRefresh.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final MyJwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = parseBearerToken(request);
            if(token != null){
                String email = jwtTokenProvider.validateUserToken(token); // 유효하지 않은 토큰인 경우 throw exception
                // 토큰이 유효하다면 인증이 완료된거임.
                AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, // 지금처럼 문자열 말고 다른 것도 들어갈 수 있는데, 예제에서는 UserDetails라는 오브젝트를 넣는 경우가 많음. 우리는 안했음.
                        null,
                        AuthorityUtils.NO_AUTHORITIES // 로그인이 된 상태에서도 관리자, 일반사용자 등의 권한이 달라질 수 있음. 여기서 설정.
                );
                // 토큰에다가 기본적인 인증정보 외에 더 부가적으로 담아놓고 싶은 내용이 있으면 아래 Detail에서 더 담는 작업 수행 가능
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 비어있는 컨텍스트 생성
                securityContext.setAuthentication(authToken); // 컨텍스트에 토큰 담고
                SecurityContextHolder.setContext(securityContext); // 홀더에 컨텍스트 고정
            }
        }catch(Exception ex){
            throw new InvalidTokenException("유효하지 않은 토큰입니다");
        }
        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
