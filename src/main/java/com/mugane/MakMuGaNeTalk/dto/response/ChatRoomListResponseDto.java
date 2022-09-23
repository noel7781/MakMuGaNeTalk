package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;

import java.time.LocalDateTime;

public class ChatRoomListResponseDto {

    private final Long id;
    private final ChatRoomType type;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isLiked;
    private final boolean isBoss;

    private ChatRoomListResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.type = chatRoom.getType();
        this.title = chatRoom.getTitle();
        this.createdAt = chatRoom.getCreatedAt();
        this.updatedAt = chatRoom.getUpdatedAt();
        this.isLiked = chatRoom.isLiked();
        this.isBoss = chatRoom.isLBoss();
    }

    public static ChatRoomListResponseDto of(ChatRoom chatRoom) {
        return new ChatRoomListResponseDto(chatRoom);
    }
}
