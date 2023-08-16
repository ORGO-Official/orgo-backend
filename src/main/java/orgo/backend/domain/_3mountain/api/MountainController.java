package orgo.backend.domain._3mountain.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orgo.backend.domain._3mountain.application.MountainService;
import orgo.backend.domain._3mountain.application.RestaurantService;
import orgo.backend.domain._3mountain.domain.PlaceInfo;
import orgo.backend.domain._3mountain.dto.MountainDto;
import orgo.backend.domain._3mountain.dto.RestaurantDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MountainController {
    private final MountainService mountainService;
    private final RestaurantService restaurantService;

    /**
     * 산 목록 조회 API
     *
     * @return 산 목록
     */
    @PermitAll
    @GetMapping("/mountains")
    public ResponseEntity<List<MountainDto.Response>> getMountains() {
        List<MountainDto.Response> responseDto = mountainService.findAll();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 근처 식당 목록 조회 API
     *
     * @param mountainId 산 아이디넘버
     * @return 근처 식당 목록
     */
    @PermitAll
    @GetMapping("/mountains/{mountainId}/restaurants")
    public ResponseEntity<List<RestaurantDto.Response>> getNearbyRestaurant(@PathVariable Long mountainId) {
        List<RestaurantDto.Response> responseDto = restaurantService.findNearbyRestaurant(mountainId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
