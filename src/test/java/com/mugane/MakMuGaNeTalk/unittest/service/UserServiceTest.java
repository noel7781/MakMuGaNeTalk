package com.mugane.MakMuGaNeTalk.unittest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.mugane.MakMuGaNeTalk.config.security.JwtTokenProvider;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.repository.RefreshTokenRepository;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import com.mugane.MakMuGaNeTalk.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;


    @Test
    void signUpTest() {
        SignUpRequestDto req = SignUpRequestDto
            .builder()
            .password("password")
            .nickname("nickname")
            .email("test@gmail.com")
            .build();
        User user = User
            .builder()
            .nickname(req.getNickname())
            .email(req.getEmail())
            .password(req.getPassword())
            .build();
        given(userRepository.save(any(User.class))).willReturn(user);

        User newUser = userService.signUp(req);

        assertThat(newUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(newUser.getNickname()).isEqualTo(user.getNickname());
    }


}
