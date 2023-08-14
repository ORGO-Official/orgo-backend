package orgo.backend.domain._3mountain.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orgo.backend.domain._3mountain.application.MountainService;
import orgo.backend.domain._3mountain.dto.MountainDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MountainController {
    private final MountainService mountainService;

    @PermitAll
    @GetMapping("/mountains")
    public ResponseEntity<List<MountainDto.Response>> getMountains() {
        List<MountainDto.Response> responseDto = mountainService.findAll();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
