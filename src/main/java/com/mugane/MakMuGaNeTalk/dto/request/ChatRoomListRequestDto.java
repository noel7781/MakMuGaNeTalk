package com.mugane.MakMuGaNeTalk.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class ChatRoomListRequestDto {

    private List<String> tagList;
    private String keyword;
    private boolean joined;

    @Builder
    ChatRoomListRequestDto(List<String> tagList, String keyword, Boolean joined) {
        this.tagList = tagList;
        this.keyword = keyword;
        this.joined = joined;
    }
}
