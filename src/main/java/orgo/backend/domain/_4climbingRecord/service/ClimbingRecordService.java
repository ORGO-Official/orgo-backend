package orgo.backend.domain._4climbingRecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.MyClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.Position;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.mapper.ClimbingRecordMapper;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._4climbingRecord.mapper.PositionMapper;
import orgo.backend.domain._4climbingRecord.repository.ClimbingRecordRepository;
import orgo.backend.domain._5badge.service.RecordBadgeFactory;
import orgo.backend.domain._6notification.service.NotificationService;
import orgo.backend.domain._6notification.vo.Notification;
import orgo.backend.domain._6notification.service.NotificationService;
import orgo.backend.global.error.exception.UserNotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClimbingRecordService {

    private final MountainRepository mountainRepository;
    private final ClimbingRecordRepository climbingRecordRepository;
    private final UserRepository userRepository;
    private final RecordBadgeFactory recordBadgeFactory;
    private final NotificationService notificationService;

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
            List<Acquisition> newBadges = recordBadgeFactory.issueAvailableBadges(user);
//            sendNotification(newBadges, user);
            return;
        }

        throw new RuntimeException();
    }

    private void sendNotification(List<Acquisition> newBadges, User user) {
        List<Notification> badgeNotifications = newBadges.stream()
                .map(acquisition -> Notification.badgeNotification(user, acquisition))
                .toList();
        notificationService.sendAllMessages(badgeNotifications);
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

        // TODO : 다시 시간순 내림차순 정렬
        List<ClimbingRecordDto> indexedClimbingRecordDtoList = new ArrayList<>();

        for (List<ClimbingRecordDto> records : climbingRecordDtoMap.values()) {
            indexedClimbingRecordDtoList.addAll(records);
        }

        indexedClimbingRecordDtoList.sort(Comparator.comparing(ClimbingRecordDto::getDate).reversed());

        MyClimbingRecordDto myClimbingRecordDto = MyClimbingRecordDto.builder()
                .climbingRecordDtoList(climbingRecordDtoList)
                .build();
        myClimbingRecordDto.setClimbingCntByMyClimbingRecordList();
        myClimbingRecordDto.setClimbedAltitudeByMyClimbingRecordList();

        return myClimbingRecordDto;
    }

    public boolean isTop(UserPosDto userPosDto) {
        final double distanceRangeKilometers = 0.5;
        final double altitudeRangeMeters = 50;
        Mountain mountain = mountainRepository.findById(userPosDto.getMountainId()).orElseThrow();
        Position userPosition = PositionMapper.INSTANCE.toPostion(userPosDto);
        Position mountainPosition = PositionMapper.INSTANCE.toPostion(mountain);

        return mountainPosition.isDistanceWithinRange(userPosition, distanceRangeKilometers) &&
                mountainPosition.isAltitudeWithinRange(userPosition, altitudeRangeMeters);
    }
}
