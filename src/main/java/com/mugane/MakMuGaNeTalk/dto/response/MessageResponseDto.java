package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.Message;
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
    private LocalDateTime createdAt;

    @Builder
    public MessageResponseDto(String email, String nickname, String content, String imgUrl,
        LocalDateTime createdAt) {
        this.email = email;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }

    // TODO IMG 관련 설정
    public static MessageResponseDto of(Message message) {
        return MessageResponseDto
            .builder()
            .email(message.getUser().getEmail())
            .nickname(message.getUser().getNickname())
            .content(message.getContent())
            .createdAt(message.getCreatedAt())
            .build();
    }
}
