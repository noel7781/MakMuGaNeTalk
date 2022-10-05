package com.mugane.MakMuGaNeTalk.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {

    private String nickname;
    private String content;
    private String imgUrl;
    private LocalDateTime createdAt;

    @Builder
    public MessageResponseDto(String nickname, String content, String imgUrl,
        LocalDateTime createdAt) {
        this.nickname = nickname;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
    }
}
