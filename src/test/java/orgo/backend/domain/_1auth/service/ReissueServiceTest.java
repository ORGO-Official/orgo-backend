package orgo.backend.domain._1auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.global.config.security.JwtProvider;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ServiceTest
class ReissueServiceTest {

    @InjectMocks
    ReissueService reissueService;

    @Mock
    JwtProvider jwtProvider;

}