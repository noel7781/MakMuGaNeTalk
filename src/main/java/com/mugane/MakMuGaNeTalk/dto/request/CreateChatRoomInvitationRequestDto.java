package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CreateChatRoomInvitationRequestDto {

    private Long guestUserId;
    private String firstMessage;
}
