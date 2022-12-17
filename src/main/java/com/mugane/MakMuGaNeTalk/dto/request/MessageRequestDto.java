package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {

    private String content;

    public MessageRequestDto(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
