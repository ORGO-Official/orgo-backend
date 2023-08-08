package orgo.backend.global.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private Long userId;

    public JwtAuthenticationToken(Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        super.setAuthenticated(true);
    }

    public JwtAuthenticationToken(Long userId) {
        super(null);
        this.userId = userId;
        super.setAuthenticated(false);
    }

    /**
     * 인증 정보가 담긴 Authentication을 생성합니다.
     *
     * @param userId      사용자의 아이디넘버
     * @param authorities 사용자의 권한
     * @return Authentication
     */
    public static JwtAuthenticationToken authenticated(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(userId, authorities);
    }

    /**
     * 인증 전의 Authentication을 생성합니다.
     *
     * @return 인증되지 않은 Authentication
     */
    public static JwtAuthenticationToken unauthenticated(Long userId) {
        return new JwtAuthenticationToken(userId);
    }


    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }
}
