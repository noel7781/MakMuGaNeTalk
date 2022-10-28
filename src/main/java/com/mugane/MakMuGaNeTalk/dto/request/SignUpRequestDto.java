package com.mugane.MakMuGaNeTalk.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequestDto {

    private String password;
    private String nickname;
    @Email
    @NotBlank
    private String email;

    @Builder
    SignUpRequestDto(String password, String nickname, String email) {
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
