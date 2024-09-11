package com.example.gccoffee.auth.withRefresh;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtTokenProvider;

    public JwtAuthFilter(JwtProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {

            String accessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);
            User user = parseSpecification(accessToken);
            AbstractAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(
                    user,
                    accessToken,
                    user.getAuthorities()
            );

            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);
        } catch (ExpiredJwtException e) {
            reissueAccessToken(request, response, e);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }


    private String parseBearerToken(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    // UserDetails 의 User
    private User parseSpecification(String token) {
        String[] split = Optional.ofNullable(token)
//                .map(jwtTokenProvider::validate)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception e) throws ExpiredJwtException {
        try {
            String refreshToken = parseBearerToken(request, "Refresh-Token");
            if (refreshToken == null) {
                throw e;
            }
            String accessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);
            jwtTokenProvider.validateRefreshToken(refreshToken, accessToken);
            String reissueToken = jwtTokenProvider.recreateAccessToken(accessToken);
            User user = parseSpecification(reissueToken);
            AbstractAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(user, reissueToken, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            response.setHeader("Re-Issue-Token", reissueToken);
        } catch (Exception ex) {
            request.setAttribute("exception", e);
        }
    }
}
