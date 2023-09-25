package orgo.backend.domain._4climbingRecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.repository.ClimbingRecordRepository;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.MyClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.domain._4climbingRecord.mapper.ClimbingRecordMapper;
import orgo.backend.domain._5badge.service.RecordBadgeFactory;
import orgo.backend.global.error.exception.UserNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClimbingRecordService {

    private final MountainRepository mountainRepository;
    private final ClimbingRecordRepository climbingRecordRepository;
    private final UserRepository userRepository;
    private final RecordBadgeFactory recordBadgeFactory;

    /**
     * 등산 완등 인증 요청을 처리합니다.
     * 이후 발급 가능한 뱃지가 있으면, 발급합니다.
     *
     * @param userId     사용자 Id
     * @param userPosDto 사용자의 위치 정보
     */
    @Transactional
    public void registerClimbingRecord(Long userId, UserPosDto userPosDto) {
        if (isTop(userPosDto)) {
            User user = userRepository.findById(userId).orElseThrow();
            ClimbingRecord climbingRecord = ClimbingRecord.builder()
                    .date(userPosDto.getDate())
                    .user(user)
                    .mountain(mountainRepository.findById(userPosDto.getMountainId()).orElseThrow())
                    .build();

            climbingRecordRepository.save(climbingRecord);
            recordBadgeFactory.issueAvailableBadges(user);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * 사용자의 모든 완등 기록을 조회합니다.
     *
     * @param userId 사용자 Id
     * @return 사용자의 완등 기록
     */
    public MyClimbingRecordDto viewMyClimbingRecords(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<ClimbingRecordDto> climbingRecordDtoList = ClimbingRecordMapper.INSTANCE.toClimbingRecordDtoList(user.getClimbingRecords());

        //TODO : 리스트를 시간순 정렬
        climbingRecordDtoList.sort(Comparator.comparing(ClimbingRecordDto::getDate));

        //TODO : 등반 회차 추가를 위해 Map으로 변환
        Map<String, List<ClimbingRecordDto>> climbingRecordDtoMap = new HashMap<>();

        climbingRecordDtoList.forEach(climbingRecordDto -> {
            if (climbingRecordDtoMap.containsKey(climbingRecordDto.getMountainName())) {
                List<ClimbingRecordDto> existedClimbingRecordDtoList = climbingRecordDtoMap.get(climbingRecordDto.getMountainName());
                existedClimbingRecordDtoList.add(climbingRecordDto);
                climbingRecordDtoMap.put(climbingRecordDto.getMountainName(), existedClimbingRecordDtoList);
            } else {
                List<ClimbingRecordDto> emptyClimbingRecordDtoList = new ArrayList<>();
                emptyClimbingRecordDtoList.add(climbingRecordDto);
                climbingRecordDtoMap.put(climbingRecordDto.getMountainName(), emptyClimbingRecordDtoList);
            }
        });

        // TODO : 등반 회차 추가
        for (Map.Entry<String, List<ClimbingRecordDto>> entry : climbingRecordDtoMap.entrySet()) {
            List<ClimbingRecordDto> climbingRecords = entry.getValue();

            for (int i = 0; i < climbingRecords.size(); i++) {
                climbingRecords.get(i).setClimbingOrder((long) (i + 1));
            }
        }

        // TODO : 다시 시간순 정렬
        List<ClimbingRecordDto> indexedClimbingRecordDtoList = new ArrayList<>();

        for (List<ClimbingRecordDto> records : climbingRecordDtoMap.values()) {
            indexedClimbingRecordDtoList.addAll(records);
        }

        indexedClimbingRecordDtoList.sort(Comparator.comparing(ClimbingRecordDto::getDate));

        MyClimbingRecordDto myClimbingRecordDto = MyClimbingRecordDto.builder()
                .climbingRecordDtoList(climbingRecordDtoList)
                .build();
        myClimbingRecordDto.setClimbingCntByMyClimbingRecordList();
        myClimbingRecordDto.setClimbedAltitudeByMyClimbingRecordList();

        return myClimbingRecordDto;
    }

    /**
     * 사용자가 완등 인증이 가능한 위치인지 판단합니다.
     *
     * @param userPosDto 사용자의 위치 정보
     * @return 완등 가능 위치인지 여부
     */
    public boolean isTop(UserPosDto userPosDto) {
        double distanceDiffRange = 500;
        double altitudeDiffRange = 50;
        Mountain mountain = mountainRepository.findById(userPosDto.getMountainId()).orElseThrow();

        double distanceDiff = calDistance(userPosDto.getLatitude(), userPosDto.getLongitude(),
                mountain.getLocation().getLatitude(), mountain.getLocation().getLongitude());

        if (distanceDiff > distanceDiffRange || Math.abs(userPosDto.getAltitude() - mountain.getLocation().getAltitude()) > altitudeDiffRange) {
            return false;
        }

        return true;
    }

    /**
     * Haversine공식을 기반으로 두 장소간의 거리를 계산합니다.
     *
     * @param latitude1  위도1
     * @param longitude1 경도1
     * @param latitude2  위도1
     * @param longitude2 경도1
     * @return 두 거리간의 거리
     */
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
