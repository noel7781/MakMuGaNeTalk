package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.request.MessageRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.MessageResponseDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.Message;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import com.mugane.MakMuGaNeTalk.repository.MessageRepository;
import com.mugane.MakMuGaNeTalk.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private UserService userService;
    private ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Transactional
    public void sendMessage(Long chatRoomId, MessageRequestDto messageRequestDto) {
        try {
            String content = messageRequestDto.getContent();
            MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .nickname("username")
                .content(content)
                .build();
            simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId + "/messages",
                messageResponseDto);
            saveMessage(chatRoomId, content);
        } catch (Exception e) {
            throw new IllegalStateException("메시지를 전송할 수 없습니다.", e);
        }
    }

    private void saveMessage(Long chatRoomId, String content) throws Exception {
        try {
            ChatRoom chatRoom = chatRoomService
                .findById(chatRoomId);
            // Todo : USER조회 ID정보로 변경
            User user = userService.findUserByNickname("username");

            Message message = Message.builder()
                .user(user)
                .chatRoom(chatRoom)
                .content(content)
                .build();
            UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

            messageRepository.save(message);
            userChatRoomRepository.save(userChatRoom);
        } catch (Exception e) {
            throw new IllegalStateException("에러가 발생했습니다. ", e);
        }
    }

}
