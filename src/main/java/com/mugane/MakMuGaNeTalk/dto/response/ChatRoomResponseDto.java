package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private String password;
    private ChatRoomType type;
    private Long likeCount;

    private List<TagResponseDto> chatRoomTagList;
    private boolean isMyFavorite;

    @Builder
    ChatRoomResponseDto(ChatRoom chatRoom, Long userId) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.password = chatRoom.getPassword();
        this.type = chatRoom.getType();
        this.chatRoomTagList = chatRoom.getChatRoomTagList().stream()
            .map(chatRoomTag -> TagResponseDto.of(chatRoomTag.getTag())
            ).collect(Collectors.toList());
        this.likeCount = (long) chatRoom.getChatRoomLikeList().size();
        this.isMyFavorite = chatRoom.getChatRoomLikeList().stream()
            .anyMatch(room -> room.getUser().getId().equals(userId));
    }

    public static ChatRoomResponseDto of(ChatRoom chatRoom, Long userId) {
        return new ChatRoomResponseDto(chatRoom, userId);
    }

}
