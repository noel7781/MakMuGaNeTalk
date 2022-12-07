package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;

@Getter
public class MessageRequestDto {

    private String content;

    public MessageRequestDto(String content) {
        this.content = content;
    }
}
