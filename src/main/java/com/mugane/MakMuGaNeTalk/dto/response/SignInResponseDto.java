package com.mugane.MakMuGaNeTalk.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResponseDto {

    String accessToken;
    String refreshToken;
}
