package com.mugane.MakMuGaNeTalk.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MessageResponseDto {

    private String email;
    private String nickname;
    private String content;
    private String imgUrl;
    private LocalDateTime createdAt;

    @Builder
    public MessageResponseDto(String email, String nickname, String content, String imgUrl,
        LocalDateTime createdAt) {
        this.email = email;
        this.nickname = nickname;
        this.content = content;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
    }
}
