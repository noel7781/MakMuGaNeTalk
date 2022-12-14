package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.config.security.JwtTokenProvider;
import com.mugane.MakMuGaNeTalk.dto.request.MessageRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomUserListDto;
import com.mugane.MakMuGaNeTalk.dto.response.MessageResponseDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.Message;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import com.mugane.MakMuGaNeTalk.enums.UserType;
import com.mugane.MakMuGaNeTalk.repository.MessageRepository;
import com.mugane.MakMuGaNeTalk.repository.UserChatRoomRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final RabbitTemplate rabbitTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final Map<Long, HashSet<User>> chatRoomUsers = new HashMap<>();

    private User getUserByAccessToken(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        return userService.findUserByEmail(authentication.getName());
    }

    @Transactional
    public void sendMessage(Long chatRoomId, String accessToken,
        MessageRequestDto messageRequestDto) {
        try {
            User user = getUserByAccessToken(accessToken);
            String content = messageRequestDto.getContent();
            MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
            rabbitTemplate.convertAndSend("amq.topic", "room." + chatRoomId, messageResponseDto);
            saveMessage(chatRoomId, user, content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("???????????? ????????? ??? ????????????.", e);
        }
    }

    @Transactional
    void saveMessage(Long chatRoomId, User user, String content) {
        try {
            ChatRoom chatRoom = chatRoomService
                .getChatRoomById(chatRoomId);

            Optional<UserChatRoom> optionalUserChatRoom = userChatRoomRepository.findByChatRoomIdAndUserId(
                chatRoomId, user.getId());

            if (optionalUserChatRoom.isEmpty()) {
                UserChatRoom userChatRoom = UserChatRoom.builder()
                    .chatRoom(chatRoom)
                    .user(user)
                    .userType(UserType.valueOf(
                        Objects.equals(chatRoom.getOwnerUser().getId(), user.getId()) ? "OWNER"
                            : "NORMAL"))
                    .build();
                userChatRoomRepository.save(userChatRoom);
            }

            Message message = Message.builder()
                .user(user)
                .chatRoom(chatRoom)
                .content(content)
                .build();
            messageRepository.save(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("????????? ??????????????????. ", e);
        }
    }

    @Transactional
    public void enter(Long chatRoomId, String accessToken) {
        try {
            User user = getUserByAccessToken(accessToken);
            chatRoomUsers.computeIfAbsent(chatRoomId, (id) -> new HashSet<>()).add(user);
            rabbitTemplate.convertAndSend("amq.topic", "roomUsers." + chatRoomId,
                ChatRoomUserListDto.of(chatRoomUsers.get(chatRoomId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void leave(Long chatRoomId, String accessToken) {
        try {
            User user = getUserByAccessToken(accessToken);
            chatRoomUsers.computeIfPresent(chatRoomId, (id, s) -> {
                s.remove(user);
                rabbitTemplate.convertAndSend("amq.topic", "roomUsers." + chatRoomId,
                    ChatRoomUserListDto.of(s));
                return s;
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
