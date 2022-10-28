package com.mugane.MakMuGaNeTalk.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugane.MakMuGaNeTalk.dto.request.SignInRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @DisplayName("회원가입 테스트")
    void signUpTest() throws Exception {

        String nickname = "tester";
        String email = "testuser@gmail.com";
        String password = "1234";

        SignUpRequestDto request = SignUpRequestDto.builder()
            .nickname(nickname)
            .email(email)
            .password(password)
            .build();

        String content = objectMapper.writeValueAsString(request);

//        given(userService.signUp(request))
//            .willReturn(SignInResponseDto
//                .builder()
//                .accessToken("accessToken")
//                .refreshToken("refreshToken")
//                .build());

        mockMvc.perform(
                post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("회원가입 테스트 - 이메일 형식 오류")
    void wrongFormatSignUpTest() throws Exception {

        String nickname = "tester";
        String email = "testuser";
        String password = "1234";

        SignUpRequestDto request = SignUpRequestDto.builder()
            .nickname(nickname)
            .email(email)
            .password(password)
            .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isBadRequest())
            .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("중복아이디 회원가입 테스트")
    void doubleSignUpTest() throws Exception {
        String nickname = "tester";
        String email = "testuser@gmail.com";
        String password = "1234";

        SignUpRequestDto request = SignUpRequestDto.builder()
            .nickname(nickname)
            .email(email)
            .password(password)
            .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isOk());

        mockMvc.perform(
                post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 테스트")
    void signInTest() throws Exception {

        String nickname = "tester";
        String email = "testuser@gmail.com";
        String password = "1234";

        SignUpRequestDto signUpRequest = SignUpRequestDto.builder()
            .nickname(nickname)
            .email(email)
            .password(password)
            .build();

        String signUpContent = objectMapper.writeValueAsString(signUpRequest);

        mockMvc.perform(
                post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(signUpContent))
            .andExpect(status().isOk());

        SignInRequestDto signInRequest = SignInRequestDto
            .builder()
            .email(email)
            .password(password)
            .build();

        String signInContent = objectMapper.writeValueAsString(signInRequest);

        mockMvc.perform(
                post("/api/v1/users/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(signInContent))
            .andExpect(status().isOk());
    }
}
