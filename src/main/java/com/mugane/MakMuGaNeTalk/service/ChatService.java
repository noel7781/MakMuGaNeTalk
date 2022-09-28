package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.MessageDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.Message;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import com.mugane.MakMuGaNeTalk.repository.MessageRepository;
import com.mugane.MakMuGaNeTalk.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private UserService userService;
    private ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public void sendMessage(String title, MessageDto messageDto) {
        try {
            simpMessagingTemplate.convertAndSend("/topic/" + title, messageDto);
            saveMessage(messageDto);
        } catch (Exception e) {
            throw new IllegalStateException("메시지를 전송할 수 없습니다.", e);
        }
    }

    public void saveMessage(MessageDto messageDto) throws Exception {
        try {
            ChatRoom chatRoom = chatRoomService
                .findByTitle(messageDto.getChatRoomTitle());
            User user = userService.findUserByNickname(messageDto.getNickname());

            Message message = Message.builder()
                .user(user)
                .chatRoom(chatRoom)
                .content(messageDto.getContent())
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
