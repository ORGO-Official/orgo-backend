package orgo.backend.global.config.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.global.error.exception.UserNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(UserNotFoundException::new);
    }
}