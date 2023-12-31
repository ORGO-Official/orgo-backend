package orgo.backend.domain._2user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import orgo.backend.domain._2user.service.UserService;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.global.error.exception.InternalServerException;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/profile")
    public ResponseEntity<UserProfileDto.Response> getProfile(@AuthenticationPrincipal Long userId) {
        UserProfileDto.Response profile = userService.getProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/users/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal Long userId, @RequestPart MultipartFile imageFile, @RequestPart UserProfileDto.Request requestDto) {
        try {
            userService.updateProfile(userId, requestDto, imageFile);
        } catch (IOException e) {
            throw new InternalServerException(e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
