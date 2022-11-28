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
    private final int currentPageNumber;
    private final int totalPageNumber;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public ChatRoomListResponseDto(
        List<ChatRoom> chatRoom,
        Long userId,
        int currentPageNumber,
        int totalPageNumber) {
        this.chatRoomList = chatRoom
            .stream()
            .map(room -> ChatRoomResponseDto.of(room, userId))
            .collect(Collectors.toList());
        this.currentPageNumber = currentPageNumber;
        this.totalPageNumber = totalPageNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
