package com.mugane.MakMuGaNeTalk.dto.request;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateChatRoomRequestDto {

    private Long userId; // TODO 추후 제거 (JWT토큰에서 정보 가져오기)
    private String title;
    private Set<String> tagList;
    private ChatRoomType chatRoomType;
    private String password;
}
