package orgo.backend.global.config.security;

import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import orgo.backend.global.constant.Header;
import orgo.backend.global.error.exception.AccessTokenExpiredException;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException, AccessTokenExpiredException {
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
