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

    @Builder
    ChatRoomListRequestDto(List<String> tagList, String keyword) {
        this.tagList = tagList;
        this.keyword = keyword;
    }
}
