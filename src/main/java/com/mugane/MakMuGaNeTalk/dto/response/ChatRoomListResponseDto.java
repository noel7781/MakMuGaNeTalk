package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {

    private final List<ChatRoomResponseDto> chatRoomList;
    private final int pageNumber;
    private final int pageCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public ChatRoomListResponseDto(List<ChatRoom> chatRoom, int pageNumber, int pageCount) {
        this.chatRoomList = chatRoom.stream().map(ChatRoomResponseDto::of).collect(
            Collectors.toList());
        this.pageNumber = pageNumber;
        this.pageCount = pageCount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

//    public static ChatRoomListResponseDto of(List<ChatRoom> chatRoomList) {
//        return new ChatRoomListResponseDto(chatRoomList);
//    }
}
