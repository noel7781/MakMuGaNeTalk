package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.UserRoleType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = {"userChatRoomList", "userBanList", "roles"})
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private Boolean fromSocial;

    @OneToMany(mappedBy = "user")
    private List<UserChatRoom> userChatRoomList;

    @OneToMany(mappedBy = "banUser")
    private List<UserBan> userBanList;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void addRole(UserRoleType userRoleType) {
        roles.add(String.valueOf(userRoleType));
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
