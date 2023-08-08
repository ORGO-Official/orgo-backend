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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String SECRET_KEY = "TemporarySecretKeyForTestAreYouUnderstandBro";
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("access");
        if (StringUtils.isNotEmpty(jwt)) {
            Claims claims = JwtValidator.validateJwt(jwt, getSigningKey(SECRET_KEY));
            Long userId = Long.parseLong(claims.getSubject());
            Authentication unauthenticatedToken = JwtAuthenticationToken.unauthenticated(userId);
            Authentication authenticatedToken = jwtAuthenticationProvider.authenticate(unauthenticatedToken);
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
        }
        filterChain.doFilter(request, response);
    }
}
