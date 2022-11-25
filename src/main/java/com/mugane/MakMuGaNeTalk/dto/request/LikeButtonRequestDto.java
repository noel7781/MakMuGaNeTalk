package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LikeButtonRequestDto {

    private Long chatRoomId;
    private Boolean likeState;
}
