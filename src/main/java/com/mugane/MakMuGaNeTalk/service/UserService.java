package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.config.security.JwtTokenProvider;
import com.mugane.MakMuGaNeTalk.dto.TokenDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignInRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.TokenRequestDto;
import com.mugane.MakMuGaNeTalk.entity.RefreshToken;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.repository.RefreshTokenRepository;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User Not Found"));
    }

    public void signUp(SignUpRequestDto signUpRequest) throws Exception {
        try {
            User user = User.builder()
                    .email(signUpRequest.getEmail())
                    .nickname(signUpRequest.getNickname())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Register Failed");
        }
    }

    public TokenDto signIn(SignInRequestDto signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 email 입니다."));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        TokenDto tokenDto = jwtTokenProvider.createTokenDto(user.getUsername(), user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;

    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new IllegalStateException("Refresh Token Expired");
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User Not Found"));

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Refresh Token Not Found"));

        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh Token Not Match");
        }

        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(String.valueOf(user.getId()),
                user.getRoles());

        RefreshToken updatedToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updatedToken);

        return newCreatedToken;
    }
}
