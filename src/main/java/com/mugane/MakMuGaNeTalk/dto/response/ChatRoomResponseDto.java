package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private ChatRoomType type;
    private Long likeCount;

    @Builder
    ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.type = chatRoom.getType();
        this.likeCount = chatRoom.getLikeCount();
    }

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return new ChatRoomResponseDto(chatRoom);
    }

}
