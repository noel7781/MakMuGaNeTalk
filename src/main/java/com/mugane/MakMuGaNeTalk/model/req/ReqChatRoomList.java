package com.mugane.MakMuGaNeTalk.model.req;

import lombok.Getter;

import java.util.List;

@Getter
public class ReqChatRoomList {

    private List<String> tagList;
    private String keyword;
    private ReqList reqList;
}
