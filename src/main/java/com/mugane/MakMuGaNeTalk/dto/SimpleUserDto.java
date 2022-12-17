package com.mugane.MakMuGaNeTalk.dto;

import com.mugane.MakMuGaNeTalk.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleUserDto {

    private Long id;
    private String nickname;

    @Builder
    public SimpleUserDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static SimpleUserDto of(User user) {
        return SimpleUserDto.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .build();
    }
}
