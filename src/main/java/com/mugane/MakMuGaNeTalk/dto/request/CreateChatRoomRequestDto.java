package com.mugane.MakMuGaNeTalk.dto.request;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateChatRoomRequestDto {

    private Long userId; // TODO 추후 제거 (JWT토큰에서 정보 가져오기)
    private String title;
    private Set<String> tagList;
    private ChatRoomType chatRoomType;
    private String password;

    @Builder
    CreateChatRoomRequestDto(Long userId, String title, Set<String> tagList,
        ChatRoomType chatRoomType, String password) {
        this.userId = userId;
        this.title = title;
        this.tagList = tagList;
        this.chatRoomType = chatRoomType;
        this.password = password;
    }
}
