package com.mugane.MakMuGaNeTalk.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInRequestDto {

    @Email
    @NotBlank
    private String email;
    private String password;

    @Builder
    SignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
