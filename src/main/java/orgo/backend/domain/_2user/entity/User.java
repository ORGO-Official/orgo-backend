package orgo.backend.domain._2user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._1auth.vo.PersonalData;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class User implements UserDetails {
    private final static String DEFAULT_ROLE = "ROLE_USER";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nickname;
    String email;
    String socialId;
    String profileImage;
    @Enumerated(value = EnumType.STRING)
    LoginType loginType;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ClimbingRecord> climbingRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Acquisition> acquisitions = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static User signup(PersonalData personalData, String profileImage) {
        return User.builder()
                .nickname(personalData.getNickname())
                .email(personalData.getEmail())
                .socialId(personalData.getSocialId())
                .loginType(personalData.getLoginType())
                .roles(Collections.singletonList(DEFAULT_ROLE))
                .profileImage(profileImage)
                .build();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }


    public boolean haveBadge(Badge badge) {
        return this.acquisitions.stream()
                .anyMatch(acquisition -> acquisition.getBadge().equals(badge));
    }

    public long countOfMountainClimbed(String mountainName) {
        return this.climbingRecords.stream()
                .filter(record -> record.getMountain().getName().equals(mountainName))
                .count();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
