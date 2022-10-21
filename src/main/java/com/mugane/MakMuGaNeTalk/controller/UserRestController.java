package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.common.ApiResponse;
import com.mugane.MakMuGaNeTalk.dto.TokenDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignInRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.TokenRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.SignInResponseDto;
import com.mugane.MakMuGaNeTalk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse<?> signUp(@RequestBody SignUpRequestDto user) {
        try {
            log.warn("user :" + user.getNickname());
            userService.signUp(user);
            return ApiResponse.success();
        } catch (Exception e) {
            return ApiResponse.error("X", "Register Failed");
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ApiResponse<?> signIn(@RequestBody SignInRequestDto user) {
        log.warn("email :" + user.getEmail());
        log.warn("password :" + user.getPassword());
        TokenDto tokenDto = userService.signIn(user);
        SignInResponseDto signInResponse = SignInResponseDto.builder()
            .accessToken(tokenDto.getAccessToken())
            .refreshToken(tokenDto.getRefreshToken())
            .build();
        return ApiResponse.success(signInResponse);
    }

    @PostMapping("/reissue")
    public ApiResponse<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ApiResponse.success(userService.reissue(tokenRequestDto));
    }

    @GetMapping("/nickname-check")
    public ApiResponse<?> checkNickname(@RequestParam String nickname) {
        return ApiResponse.success(userService.checkNickname(nickname));
    }

    @GetMapping("/email-check")
    public ApiResponse<?> checkEmail(@RequestParam String email) {
        return ApiResponse.success(userService.checkEmail(email));
    }

    @GetMapping("/test")
    public String test() {
        return "<h1>성공</h1>";
    }
}
