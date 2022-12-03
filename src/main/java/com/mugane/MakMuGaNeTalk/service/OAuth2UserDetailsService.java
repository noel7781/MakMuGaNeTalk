package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.request.UserDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.UserRoleType;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuth2UserDetailsService(UserRepository userRepository,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = "";
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }
        User user = saveSocialUser(email);

        UserDto userDto = UserDto
            .builder()
            .id(user.getId())
            .email(email)
            .password(user.getPassword())
            .nickname(email)
            .fromSocial(true)
            .roles(
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(
                    Collectors.toList()))
            .attr(oAuth2User.getAttributes())
            .build();

        return userDto;
    }


    private User saveSocialUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        User user = User
            .builder()
            .email(email)
            .nickname(email)
            .password(passwordEncoder.encode("1234"))
            .fromSocial(true)
            .build();

        user.addRole(UserRoleType.ROLE_USER);
        userRepository.save(user);
        return user;
    }
}
