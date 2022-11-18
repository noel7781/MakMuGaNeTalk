package com.mugane.MakMuGaNeTalk.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomListRequestDto {

    private List<String> tagList;
    private String keyword;
    private ListRequestDto reqList = new ListRequestDto();

    @Builder
    ChatRoomListRequestDto(List<String> tagList, String keyword, ListRequestDto reqList) {
        this.tagList = tagList;
        this.keyword = keyword;
        this.reqList = reqList;
    }
}
