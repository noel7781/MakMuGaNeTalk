package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ChangeNicknameRequestDto {

    private Long userId;
    private String nickname;

}
