package orgo.backend.global.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import orgo.backend.global.constant.Header;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(Header.AUTH);
        if (StringUtils.isNotEmpty(jwt)) {
            Claims claims = jwtProvider.parseToClaims(jwt);
            Long userId = Long.parseLong(claims.getSubject());
            Authentication unauthenticatedToken = JwtAuthenticationToken.unauthenticated(userId);
            Authentication authenticatedToken = jwtAuthenticationProvider.authenticate(unauthenticatedToken);
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
        }
        filterChain.doFilter(request, response);
    }
}
