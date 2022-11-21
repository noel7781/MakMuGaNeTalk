package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.TokenDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignInRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.TokenRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.SignInResponseDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.exception.CustomException;
import com.mugane.MakMuGaNeTalk.exception.ErrorCode;
import com.mugane.MakMuGaNeTalk.service.UserService;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Validated @RequestBody SignUpRequestDto user,
        Errors errors) {
        if (errors.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        Long userId = userService.signUp(user).getId();
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(@Validated @RequestBody SignInRequestDto user,
        Errors errors) {
        if (errors.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        TokenDto tokenDto = userService.signIn(user);
        SignInResponseDto signInResponse = SignInResponseDto.builder()
            .accessToken(tokenDto.getAccessToken())
            .refreshToken(tokenDto.getRefreshToken())
            .build();
        return ResponseEntity.status(HttpStatus.OK).body(signInResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal User user) {

        userService.deleteRefreshToken(user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.reissue(tokenRequestDto));
    }

    @GetMapping("/nickname-check")
    public ResponseEntity<Long> checkNickname(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNickname(nickname));
    }

    @GetMapping("/email-check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam @Email @NotBlank String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(email));
    }
}
