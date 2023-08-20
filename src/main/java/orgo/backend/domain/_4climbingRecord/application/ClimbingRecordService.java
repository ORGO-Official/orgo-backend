package orgo.backend.domain._4climbingRecord.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.domain._4climbingRecord.dao.ClimbingRecordRepository;
import orgo.backend.domain._4climbingRecord.domain.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimbingRecordService {

    private final MountainRepository mountainRepository;
    private final ClimbingRecordRepository climbingRecordRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerClimbingRecord(Long userId, UserPosDto userPosDto) {
        if(isTop(userPosDto)) {

            ClimbingRecord climbingRecord = ClimbingRecord.builder()
                    .date(userPosDto.getDate())
                    .user(userRepository.findById(userId).orElseThrow())
                    .mountain(mountainRepository.findById(userPosDto.getMountainId()).orElseThrow())
                    .build();

            climbingRecordRepository.save(climbingRecord);
        }else {
            throw new RuntimeException();
        }
    }

    public List<ClimbingRecordDto> viewMyClimbingRecords(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<ClimbingRecord> climbingRecords = user.getClimbingRecords();

        List<ClimbingRecordDto> climbingRecordDtos = new ArrayList<>();

        for(ClimbingRecord climbingRecord : climbingRecords) {
            ClimbingRecordDto climbingRecordDto = ClimbingRecordDto.builder()
                    .id(climbingRecord.getId())
                    .mountainId(climbingRecord.getMountain().getId())
                    .mountainName(climbingRecord.getMountain().getName())
                    .date(climbingRecord.getDate())
                    .build();

            climbingRecordDtos.add(climbingRecordDto);
        }

        return climbingRecordDtos;
    }

    public boolean isTop(UserPosDto userPosDto) {
        double distanceDiffRange = 500;
        double altitudeDiffRange = 50;
        Mountain mountain = mountainRepository.findById(userPosDto.getMountainId()).orElseThrow();

        double distanceDiff = calDistance(userPosDto.getLatitude(), userPosDto.getLongitude(),
                mountain.getLocation().getLatitude(), mountain.getLocation().getLongitude());

        if(distanceDiff > distanceDiffRange || Math.abs(userPosDto.getAltitude()-mountain.getLocation().getAltitude()) > altitudeDiffRange) {
            return false;
        }

        return true;
    }

    public double calDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double RADIUS_OF_EARTH_KM = 6371.0;
        double latitudeRad1 = Math.toRadians(latitude1);
        double longitudeRad1 = Math.toRadians(longitude1);
        double latitudeRad2 = Math.toRadians(latitude2);
        double LongitudeRad2 = Math.toRadians(longitude2);

        double latitudeDiff = latitudeRad2 - latitudeRad1;
        double LongitudeDiff = LongitudeRad2 - longitudeRad1;

        double a = Math.sin(latitudeDiff / 2) * Math.sin(latitudeDiff / 2) +
                Math.cos(latitudeRad1) * Math.cos(latitudeRad2) *
                        Math.sin(LongitudeDiff / 2) * Math.sin(LongitudeDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS_OF_EARTH_KM * c;
    }
}
