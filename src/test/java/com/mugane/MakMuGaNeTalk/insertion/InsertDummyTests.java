package com.mugane.MakMuGaNeTalk.insertion;

import com.mugane.MakMuGaNeTalk.config.security.JwtTokenProvider;
import com.mugane.MakMuGaNeTalk.dto.request.MessageRequestDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.enums.UserRoleType;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import com.mugane.MakMuGaNeTalk.service.ChatService;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class InsertDummyTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @Disabled
    public void insertDummies() {
        int userCount = 3;
        int chatRoomCount = 2;
        int messageCount = 2400;
        // Dummy User
        IntStream.rangeClosed(1, userCount).forEach(i -> {
            User user = User.builder()
                .email("user" + i + "@test.com")
                .nickname("user" + i)
                .fromSocial(false)
                .password(passwordEncoder.encode("1234"))
                .build();

            user.addRole(UserRoleType.ROLE_USER);
            if (i % 10 == 1) {
                user.addRole(UserRoleType.ROLE_ADMIN);
            }
            userRepository.save(user);
        });
        // Dummy Chatroom
        LongStream.rangeClosed(1, chatRoomCount).forEach(i -> {
            chatRoomService.createChatRoom(i % userCount + 1, ChatRoomType.OPEN_CHAT, "room" + i,
                "",
                List.of("tag1", "tag2", "tag3"));
        });
        // Dummy Message
        LongStream.rangeClosed(1, messageCount).forEach(i -> {
            Long userId = i % userCount + 1;
            String userPk = "user" + userId + "@test.com";
            String nickname = "user" + userId;
            String accessToken = jwtTokenProvider.createAccessToken(userId, userPk, nickname,
                List.of("ROLE_USER"));
            MessageRequestDto requestDto = new MessageRequestDto(i + "번째 메시지: " +
                userId + " 가 말합니다.");
            chatService.sendMessage(i % chatRoomCount + 1, accessToken, requestDto);
        });
    }
}
