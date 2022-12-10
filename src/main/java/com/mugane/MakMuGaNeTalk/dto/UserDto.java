package com.mugane.MakMuGaNeTalk.dto;

import com.mugane.MakMuGaNeTalk.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ToString
@Slf4j
@Getter
public class UserDto implements OAuth2User, UserDetails {

    private Long id;
    private String password;
    private String nickname;
    private String email;
    private boolean fromSocial;
    private List<GrantedAuthority> roles;
    private Map<String, Object> attr;

    @Builder
    public UserDto(Long id, String password, String nickname, String email, boolean fromSocial,
        List<GrantedAuthority> roles, Map<String, Object> attr) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.fromSocial = fromSocial;
        this.roles = roles;
        this.attr = attr;
    }

    public static UserDto of(User user) {
        return UserDto.builder()
            .id(user.getId())
            .password(user.getPassword())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .fromSocial(user.getFromSocial())
            .roles(user.getRoles().stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()))
            .build();
    }

    public User toEntity() {
        return User.builder()
            .id(this.id)
            .email(this.email)
            .nickname(this.nickname)
            .roles(this.roles.stream().map(Object::toString).collect(Collectors.toList()))
            .build();
    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
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


    @Override
    public String getName() {
        return this.email;
    }
}
