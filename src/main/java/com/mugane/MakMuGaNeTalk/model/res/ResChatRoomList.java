package com.mugane.MakMuGaNeTalk.model.res;

import com.mugane.MakMuGaNeTalk.model.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;

import java.time.LocalDateTime;

public class ResChatRoomList {

    private final Long id;
    private final ChatRoomType type;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isLiked;
    private final boolean isBoss;

    private ResChatRoomList(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.type = chatRoom.getType();
        this.title = chatRoom.getTitle();
        this.createdAt = chatRoom.getCreatedAt();
        this.updatedAt = chatRoom.getUpdatedAt();
        this.isLiked = chatRoom.isLiked();
        this.isBoss = chatRoom.isLBoss();
    }

    public static ResChatRoomList of(ChatRoom chatRoom) {
        return new ResChatRoomList(chatRoom);
    }
}
