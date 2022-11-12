package com.mugane.MakMuGaNeTalk.dto.request;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CreateChatRoomRequestDto {

    private String title;
    private Set<String> tagList;
    private ChatRoomType chatRoomType;
    private String password;

    @Builder
    CreateChatRoomRequestDto(String title, Set<String> tagList,
        ChatRoomType chatRoomType, String password) {
        this.title = title;
        this.tagList = tagList;
        this.chatRoomType = chatRoomType;
        this.password = password;
    }
}
