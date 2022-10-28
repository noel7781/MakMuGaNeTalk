package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomListRequestDto {

    private List<String> tagList;
    private String keyword;
    private ListRequestDto reqList;
}
