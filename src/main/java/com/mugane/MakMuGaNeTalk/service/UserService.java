package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.config.security.JwtTokenProvider;
import com.mugane.MakMuGaNeTalk.dto.TokenDto;
import com.mugane.MakMuGaNeTalk.dto.request.ChangeNicknameRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignInRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.SignUpRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.TokenRequestDto;
import com.mugane.MakMuGaNeTalk.entity.RefreshToken;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.exception.CustomException;
import com.mugane.MakMuGaNeTalk.exception.ErrorCode;
import com.mugane.MakMuGaNeTalk.repository.RefreshTokenRepository;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User Not Found"));
    }

    @Transactional
    public User signUp(SignUpRequestDto signUpRequest) throws CustomException {
        try {
            User user = User.builder()
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .fromSocial(false)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
            return userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    @Transactional
    public TokenDto signIn(SignInRequestDto signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 email 입니다."));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        TokenDto tokenDto = jwtTokenProvider.createTokenDto(
            user.getId(),
            user.getUsername(),
            user.getNickname(),
            user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
            .userId(user.getId())
            .token(tokenDto.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;

    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        log.warn("token = {}", tokenRequestDto);
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new IllegalStateException("Refresh Token Expired");
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new IllegalStateException("User Not Found"));

        RefreshToken refreshToken = refreshTokenRepository.findByToken(
                tokenRequestDto.getRefreshToken())
            .orElseThrow(() -> new IllegalStateException("Refresh Token Not Found"));

        if (!Objects.equals(refreshToken.getUserId(), user.getId())) {
            throw new IllegalArgumentException("Refresh Token Not Match");
        }

        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getRoles());

        RefreshToken updatedToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updatedToken);

        return newCreatedToken;
    }

    @Transactional(readOnly = true)
    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new IllegalArgumentException("해당 닉네임을 가지는 회원이 없습니다."));
    }

    @Transactional(readOnly = true)
    public Long checkNickname(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()) {
            return user.get().getId();
        }
        return 0L;
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Transactional
    public void deleteRefreshToken(Long userId) {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findRefreshTokensByUserId(
            userId);
        refreshTokenRepository.deleteAll(refreshTokenList);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가지는 회원이 없습니다."));
    }

    @Transactional
    public TokenDto oauthGoogle(String credential) {
        Map<String, String> attrs = JwtTokenProvider.getPayloadByToken(credential);

        String email = attrs.get("email");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;
        if (optionalUser.isEmpty()) {
            user = User.builder()
                .email(attrs.get("email"))
                .nickname(attrs.get("given_name"))
                .password(passwordEncoder.encode(attrs.get("sub")))
                .fromSocial(true)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
            userRepository.save(user);
        } else {
            user = optionalUser.get();
        }

        TokenDto tokenDto = jwtTokenProvider.createTokenDto(
            user.getId(),
            user.getUsername(),
            user.getNickname(),
            user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
            .userId(user.getId())
            .token(tokenDto.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    public boolean changeNickname(ChangeNicknameRequestDto requestDto) {
        Long userId = requestDto.getUserId();
        String newNickname = requestDto.getNickname();
        Optional<User> optionalUser = userRepository.findByNickname(newNickname);
        if (optionalUser.isPresent()) {
            return false;
        }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가지는 유저가 없습니다."));
        user.changeNickname(newNickname);
        userRepository.save(user);
        return true;
    }
}
