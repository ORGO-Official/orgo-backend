package orgo.backend.integrationtest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.setting.IntegrationTest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthenticationFilterTest extends IntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("jwt 인증 필터 테스트")
    void test1() throws Exception {
        // given
        User user = new User();
        userRepository.save(user);

        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(1L));
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date((new Date()).getTime() + 100000));
        claims.setId(UUID.randomUUID().toString());
        SecretKey key = Keys.hmacShaKeyFor("TemporarySecretKeyForTestAreYouUnderstandBro".getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //when
        ResultActions actions = mvc.perform(post("/test/jwt")
                .contentType(MediaType.APPLICATION_JSON)
                .header("access", jwt));

        //then
        actions.andExpect(status().isOk());
    }
}
