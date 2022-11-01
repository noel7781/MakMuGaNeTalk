package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomListResponseDto {

    private final List<ChatRoomResponseDto> chatRoomList;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ChatRoomListResponseDto(List<ChatRoom> chatRoom) {
        this.chatRoomList = chatRoom.stream().map(ChatRoomResponseDto::of).collect(
            Collectors.toList());
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static ChatRoomListResponseDto of(List<ChatRoom> chatRoomList) {
        return new ChatRoomListResponseDto(chatRoomList);
    }
}
