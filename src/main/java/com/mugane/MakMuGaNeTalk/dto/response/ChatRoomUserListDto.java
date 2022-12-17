package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.dto.SimpleUserDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomUserListDto {

    private List<SimpleUserDto> userList;

    @Builder
    public ChatRoomUserListDto(List<SimpleUserDto> userList) {
        this.userList = userList;
    }

    public static ChatRoomUserListDto of(Set<User> s) {
        return ChatRoomUserListDto.builder()
            .userList(s.stream().map(SimpleUserDto::of).collect(Collectors.toList()))
            .build();
    }
}
