package com.mugane.MakMuGaNeTalk.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MessageDto {

    private String nickname;
    private String chatRoomTitle;
    private String content;

}
